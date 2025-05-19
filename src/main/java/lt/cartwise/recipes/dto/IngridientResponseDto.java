package lt.cartwise.recipes.dto;

import lt.cartwise.Unit;

public class IngridientResponseDto {
	
	private Double amount;
	private Unit units;
	
	
	public IngridientResponseDto(Double amount, Unit units) {
		this.amount = amount;
		this.units = units;
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
	
	
	
}
