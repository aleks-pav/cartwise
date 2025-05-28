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
import lt.cartwise.shopping.repositories.ShoppingListRepository;
import lt.cartwise.user.dto.UserDto;

@Service
public class ShoppingListService {
	
	private ShoppingListRepository shoppingListRepository;
	private ProductMapper productMapper;
	private final PlanService planService;
	
	public ShoppingListService(ShoppingListRepository shoppingListRepository, ProductMapper productMapper, PlanService planService) {
		this.shoppingListRepository = shoppingListRepository;
		this.productMapper = productMapper;
		this.planService = planService;
	}

	
	public Optional<ShoppingListDto> getShoppingList(String id) {
		return shoppingListRepository.findById(id).map( this::toDto );
	}
	
	@Transactional
	public void createShoppingList(@Valid ShoppingListCreateDto dto) {
		Plan plan = planService.getPlan(dto.getPlanId(), new UserDto(dto.getUserId())).orElseThrow( () -> new NotFoundException("Plan by user not found") );
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setPlan(plan);
		
		List<ShoppingListProduct> shoppingProducts = null;
		shoppingList.setProducts(shoppingProducts);
		
		shoppingListRepository.save( shoppingList );
	}
	
	
	
	private ShoppingListDto toDto(ShoppingList entity) {
		return new ShoppingListDto( entity.getProducts().stream().map( this::toShoppingListProductDto ).toList() );
	}
	
	private ShoppingListProductDto toShoppingListProductDto(ShoppingListProduct entity) {
		return new ShoppingListProductDto(entity.getId()
				, entity.getAmount()
				, entity.getUnits()
				, entity.getIsCompleted()
				, productMapper.toDto(entity.getProduct()));
	}


	
}
