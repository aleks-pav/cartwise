package lt.cartwise.recipe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lt.cartwise.enums.Unit;

public record IngridientPostRequest(
		@NotNull
		@Min(1)
		Long productId,
		Unit units,
		
		@Positive(message = "Amount can't be negative")
		Double amount
		
		) {}
