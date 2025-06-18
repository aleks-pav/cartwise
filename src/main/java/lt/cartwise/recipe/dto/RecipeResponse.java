package lt.cartwise.recipe.dto;

import java.time.LocalDateTime;

public record RecipeResponse(Long id, String name, Boolean isVerified, LocalDateTime createdAt) {

}
