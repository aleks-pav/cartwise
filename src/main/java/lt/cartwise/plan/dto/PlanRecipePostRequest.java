package lt.cartwise.plan.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lt.cartwise.enums.MealType;

public record PlanRecipePostRequest(
		@NotNull(message = "Portions amount required")
		Double portions,
		
		@FutureOrPresent(message = "Plan date must be today or in the future")
		LocalDate planDate,
		
		MealType type,
		
		@NotNull(message = "Recipe id is required")
		Long recipe_id
		
		) {}
