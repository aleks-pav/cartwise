package lt.cartwise.plan.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record PlanRecipePostRequestList(
		@NotEmpty(message = "Body can't be empty")
		@Valid
		List<PlanRecipePostRequest> recipes
		
		) {}
