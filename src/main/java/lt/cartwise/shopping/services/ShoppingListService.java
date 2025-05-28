package lt.cartwise.shopping.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lt.cartwise.product.mappers.ProductMapper;
import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.dto.ShoppingListProductDto;
import lt.cartwise.shopping.entities.ShoppingList;
import lt.cartwise.shopping.entities.ShoppingListProduct;
import lt.cartwise.shopping.repositories.ShoppingListRepository;

@Service
public class ShoppingListService {
	
	private ShoppingListRepository shoppingListRepository;
	private ProductMapper productMapper;
	
	public ShoppingListService(ShoppingListRepository shoppingListRepository, ProductMapper productMapper) {
		this.shoppingListRepository = shoppingListRepository;
		this.productMapper = productMapper;
	}

	
	public Optional<ShoppingListDto> getShoppingList(String id) {
		return shoppingListRepository.findById(id).map( this::toDto );
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
