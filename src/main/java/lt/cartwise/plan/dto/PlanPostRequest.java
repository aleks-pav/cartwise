package lt.cartwise.plan.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PlanPostRequest(
		@Size(min = 3, message = "Plan name must contain at least 3 symbols")
		@NotEmpty(message = "Plan name field is required")
		String name
		
		) {}
