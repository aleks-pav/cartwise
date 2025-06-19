package lt.cartwise.recipe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.services.RecipeCategoryService;

@RestController
@RequestMapping("/api/recipes/categories")
public class RecipeCategoryController {

	private final RecipeCategoryService recipeCategoryService;

	public RecipeCategoryController(RecipeCategoryService recipeCategoryService) {
		this.recipeCategoryService = recipeCategoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<RecipeCategoryDto>> getAllActive(@RequestParam String lng){
		if (!List.of("EN", "LT", "FR", "DE").contains(lng.toUpperCase())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok( recipeCategoryService.getAllActive(lng) );
	}
}
