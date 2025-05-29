package lt.cartwise.plan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlanDeleteDto {

	@NotNull(message = "Plan id field is required")
	private Long id;
	
	@NotNull(message = "UserId field is required")
	private Long userId;

	public PlanDeleteDto(Long id, Long userId) {
		super();
		this.id = id;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	


	
	
	
}
