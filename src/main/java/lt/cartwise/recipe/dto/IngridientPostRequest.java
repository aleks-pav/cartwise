package lt.cartwise.recipe.dto;

import lt.cartwise.enums.Unit;

public record IngridientPostRequest(

		Long productId,
		Unit units,
		Double amount
		
		) {}
