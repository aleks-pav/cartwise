package lt.cartwise.shopping.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.services.PlanService;
import lt.cartwise.product.mappers.ProductMapper;
import lt.cartwise.shopping.dto.ShoppingListCreateDto;
import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.dto.ShoppingListProductDto;
import lt.cartwise.shopping.entities.ShoppingList;
import lt.cartwise.shopping.entities.ShoppingListProduct;
import lt.cartwise.shopping.mappers.ShoppingListMapper;
import lt.cartwise.shopping.repositories.ShoppingListRepository;
import lt.cartwise.user.dto.UserDto;

@Service
public class ShoppingListService {
	private final ShoppingListRepository shoppingListRepository;
	private final ShoppingListMapper shoppingListMapper;
	private final PlanService planService;
	
	public ShoppingListService(ShoppingListRepository shoppingListRepository, ShoppingListMapper shoppingListMapper, PlanService planService) {
		this.shoppingListRepository = shoppingListRepository;
		this.shoppingListMapper = shoppingListMapper;
		this.planService = planService;
	}

	
	public Optional<ShoppingListDto> getShoppingList(String id) {
		return shoppingListRepository.findById(id).map( shoppingListMapper::toDto );
	}
	
	@Transactional
	public String createShoppingList(@Valid ShoppingListCreateDto dto) {
		Plan plan = planService.getPlan(dto.getPlanId(), new UserDto(dto.getUserId())).orElseThrow( () -> new NotFoundException("Plan by user not found") );
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setPlan(plan);
		
		List<ShoppingListProduct> shoppingProducts = planService.calculateProducts( plan.getId() ).entrySet().stream()
				.flatMap( entryProduct ->
				entryProduct.getValue().entrySet().stream().map( entryUnit -> {
					ShoppingListProduct shoppingProduct = new ShoppingListProduct();
					shoppingProduct.setProduct( entryProduct.getKey() );
					shoppingProduct.setUnits( entryUnit.getKey() );
					shoppingProduct.setAmount( entryUnit.getValue() );
					shoppingProduct.setIsCompleted(false);
					shoppingProduct.setShoppingList(shoppingList);
					return shoppingProduct;
				})
		).toList();
		
		shoppingList.setProducts(shoppingProducts);
		
		return shoppingListRepository.save( shoppingList ).getId();
	}
}
