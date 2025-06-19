package lt.cartwise.shopping.dto;

import java.util.List;

public class ShoppingListDto {

	private String name;
	private List<ShoppingListProductDto> products;

	public ShoppingListDto(String name, List<ShoppingListProductDto> products) {
		this.name = name;
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShoppingListProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ShoppingListProductDto> products) {
		this.products = products;
	}

}
