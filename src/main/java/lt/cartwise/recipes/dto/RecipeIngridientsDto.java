package lt.cartwise.recipes.dto;

import lt.cartwise.enums.Unit;
import lt.cartwise.products.dto.ProductIngridientDto;

public class RecipeIngridientsDto {
	
	private Double amount;
	private Unit units;
	private ProductIngridientDto product;
	
	
	public RecipeIngridientsDto(Double amount, Unit units, ProductIngridientDto product) {
		this.amount = amount;
		this.units = units;
		this.product = product;
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
	public ProductIngridientDto getProduct() {
		return product;
	}
	public void setProduct(ProductIngridientDto product) {
		this.product = product;
	}
}
