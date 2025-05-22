package lt.cartwise.recipes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.recipes.dto.RecipeWithAttributesDto;
import lt.cartwise.recipes.services.RecipeService;

@RestController
@CrossOrigin
@RequestMapping("/api/recipes")
public class RecipeController {
	
	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping
	public ResponseEntity<List<RecipeWithAttributesDto>> getAllPublic(){
		return ResponseEntity.ok( recipeService.getAllPublic() );
//		return ResponseEntity.status(402).build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		if( recipeService.deleteById(id) )
			return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}
}
