package lt.cartwise.shopping.dto;

import java.util.List;


public class ShoppingListDto {
	
	private List<ShoppingListProductDto> products;

	public ShoppingListDto(List<ShoppingListProductDto> products) {
		this.products = products;
	}

	
	public List<ShoppingListProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ShoppingListProductDto> products) {
		this.products = products;
	}
	
	
}
