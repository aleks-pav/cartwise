package lt.cartwise.shopping.dto;

import jakarta.validation.constraints.*;

public class ShoppingListCreateDto {
	
	@NotEmpty(message = "Plan id is required")
	private Long planId;
	@NotEmpty(message = "User id is required")
	private Long userId;
	
	public ShoppingListCreateDto(Long planId, Long userId) {
		this.planId = planId;
		this.userId = userId;
	}

	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}
