package lt.cartwise.recipe.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.services.RecipeCategoryService;

@RestController
@CrossOrigin
@RequestMapping("/api/recipes/categories")
public class RecipeCategoryController {

	private final RecipeCategoryService recipeCategoryService;

	public RecipeCategoryController(RecipeCategoryService recipeCategoryService) {
		this.recipeCategoryService = recipeCategoryService;
	}
	
//	@GetMapping
//	public ResponseEntity<List<RecipeCategoryDto>> getAll(){
//		return ResponseEntity.ok( recipeCategoryService.getAll() );
//	}
	
	@GetMapping
	public ResponseEntity<List<RecipeCategoryDto>> getAll(@RequestParam String lng){
		return ResponseEntity.ok( recipeCategoryService.getAll(lng) );
	}
}
