package lt.cartwise.recipes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.recipes.dto.RecipeCategoryDto;
import lt.cartwise.recipes.services.RecipeCategoryService;

@RestController
@CrossOrigin
@RequestMapping("/api/recipes/categories")
public class RecipeCategoryController {

	private RecipeCategoryService recipeCategoryService;

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
