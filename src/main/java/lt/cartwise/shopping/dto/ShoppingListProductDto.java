package lt.cartwise.shopping.dto;

import lt.cartwise.enums.Unit;
import lt.cartwise.product.dto.ProductDto;

public class ShoppingListProductDto {

	private Long id;
	private Double amount;
	private Unit units;
	private Boolean isCompleted;
	private ProductDto product;

	public ShoppingListProductDto(Long id, Double amount, Unit units, Boolean isCompleted, ProductDto product) {
		this.id = id;
		this.amount = amount;
		this.units = units;
		this.isCompleted = isCompleted;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Unit getUnits() {
		return units;
	}

	public void setUnits(Unit units) {
		this.units = units;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

}
