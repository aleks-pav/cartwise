package lt.cartwise.shopping.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.product.mappers.ProductMapper;
import lt.cartwise.shopping.dto.ShoppingListDto;
import lt.cartwise.shopping.dto.ShoppingListProductDto;
import lt.cartwise.shopping.entities.ShoppingList;
import lt.cartwise.shopping.entities.ShoppingListProduct;

@Component
public class ShoppingListMapper {
	private final ProductMapper productMapper;
	
	public ShoppingListMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	
	
	public ShoppingListDto toDto(ShoppingList entity) {
		return new ShoppingListDto( entity.getPlan().getName()
				, entity.getProducts().stream().map( this::toShoppingListProductDto ).toList() );
	}
	
	public ShoppingListProductDto toShoppingListProductDto(ShoppingListProduct entity) {
		return new ShoppingListProductDto(entity.getId()
				, entity.getAmount()
				, entity.getUnits()
				, entity.getIsCompleted()
				, productMapper.toDto(entity.getProduct()));
	}
}
