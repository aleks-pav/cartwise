package lt.cartwise.recipe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecipePatchRequest(

		@NotNull(message = "Recipe id is required")
		@Positive(message = "Recipe id must be a positive integer")
		Long id,
		
		@NotNull(message = "Recipe isVerified required")
		Boolean isVerified) {

}
