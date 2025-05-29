package lt.cartwise.plan.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlanCreateDto {

	@Size(min = 3, message = "Plan name must contain at least 3 symbols")
	@NotEmpty(message = "Plan name field is required")
	private String name;
	
	@NotNull(message = "UserId field is required")
	private Long userId;
	
	public PlanCreateDto(String name, Long userId) {
		this.name = name;
		this.userId = userId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
	
}
