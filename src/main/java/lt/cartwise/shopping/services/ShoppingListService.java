package lt.cartwise.shopping.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lt.cartwise.enums.Unit;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.product.entities.Product;
import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.entities.ShoppingList;
import lt.cartwise.shopping.entities.ShoppingListProduct;
import lt.cartwise.shopping.mappers.ShoppingListMapper;
import lt.cartwise.shopping.repositories.ShoppingListRepository;

@Service
public class ShoppingListService {
	private final ShoppingListRepository shoppingListRepository;
	private final ShoppingListMapper shoppingListMapper;

	public ShoppingListService(ShoppingListRepository shoppingListRepository, ShoppingListMapper shoppingListMapper) {
		this.shoppingListRepository = shoppingListRepository;
		this.shoppingListMapper = shoppingListMapper;
	}

	public Optional<ShoppingListDto> getOptionalDtoById(String id) {
		return shoppingListRepository.findById(id).map(shoppingListMapper::toDto);
	}

	@Transactional
	public String createShoppingList(Plan plan, Map<Product, Map<Unit, Double>> products) {
		Optional<ShoppingList> oldShoppingList = shoppingListRepository.findByPlanId(plan.getId());
		oldShoppingList.ifPresent((s) -> {
			plan.setShoppingList(null);
			shoppingListRepository.delete(s);
			shoppingListRepository.flush();
		});

		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setPlan(plan);

		List<ShoppingListProduct> shoppingProducts = products.entrySet().stream()
				.flatMap(entryProduct -> entryProduct.getValue().entrySet().stream().map(entryUnit -> {
					ShoppingListProduct shoppingProduct = new ShoppingListProduct();
					shoppingProduct.setProduct(entryProduct.getKey());
					shoppingProduct.setUnits(entryUnit.getKey());
					shoppingProduct.setAmount(entryUnit.getValue());
					shoppingProduct.setIsCompleted(false);
					shoppingProduct.setShoppingList(shoppingList);
					return shoppingProduct;
				})).toList();

		shoppingList.setProducts(shoppingProducts);

		return shoppingListRepository.save(shoppingList).getId();
	}
}
