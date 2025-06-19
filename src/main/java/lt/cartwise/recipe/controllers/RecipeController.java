package lt.cartwise.recipe.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.services.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	public ResponseEntity<List<RecipeWithAttributesDto>> getAllPublic() {
		return ResponseEntity.ok(recipeService.getAllByIsPublicIsVerified(true, true));
	}

	@GetMapping("{id}")
	public ResponseEntity<RecipeWithAttributesDto> getPublicById(@PathVariable Long id) {
		return ResponseEntity.of(recipeService.getOptionalDtoByIdIsPublic(true, id));
	}
}
