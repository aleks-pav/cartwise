package lt.cartwise.plan.dto;

import jakarta.validation.constraints.Size;

public class PlanCreateDto {

	@Size(min = 3, message = "Plan name must contain at least 3 symbols")
	private String name;

	public PlanCreateDto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
