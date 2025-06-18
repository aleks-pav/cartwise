package lt.cartwise.recipe.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.cartwise.translations.Translation;

public record RecipePostRequest (
		
		@Size(min = 2, message = "Name can't be shorter than 2 symbols")
		String name,
		
		@NotNull
		@Min(1)
		@Max(12)
		Integer portions,
		
		Integer timePreparation,
		Integer timeCooking,
		
		@NotNull
		Boolean isPublic,
		
		@NotEmpty(message = "Categories can't be empty")
		List<Long> categories,
		
		@NotEmpty(message = "Ingridients can't be empty")
		@Valid
		List<@Valid IngridientPostRequest> ingridients,

		@NotNull(message = "Translations can't be null")
		List<Translation> translations
		
		) {}
