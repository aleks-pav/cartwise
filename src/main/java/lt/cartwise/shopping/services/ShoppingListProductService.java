package lt.cartwise.shopping.services;

import org.springframework.stereotype.Service;

import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.shopping.dto.ShoppingListProductDto;
import lt.cartwise.shopping.entities.ShoppingListProduct;
import lt.cartwise.shopping.mappers.ShoppingListMapper;
import lt.cartwise.shopping.repositories.ShoppingListProductRepository;

@Service
public class ShoppingListProductService {
	
	private final ShoppingListProductRepository shoppingListProductRepository;
	private final ShoppingListMapper shoppingListMapper;

	public ShoppingListProductService(ShoppingListProductRepository shoppingListProductRepository, ShoppingListMapper shoppingListMapper) {
		this.shoppingListProductRepository = shoppingListProductRepository;
		this.shoppingListMapper = shoppingListMapper;
	}

	public ShoppingListProductDto switchCompleted(String id, ShoppingListProductDto dto) {
		ShoppingListProduct listProduct = shoppingListProductRepository
				.findByIdAndShoppingListId(dto.getId(), id)
				.orElseThrow(() -> new NotFoundException("not found"));
		listProduct.setIsCompleted( !listProduct.getIsCompleted() );
		return shoppingListMapper.toShoppingListProductDto( shoppingListProductRepository.save(listProduct) );
	}
	
	
	
}
