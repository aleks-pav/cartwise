package lt.cartwise.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.UserNotFoundException;
import lt.cartwise.recipe.dto.RecipeCreateDto;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.services.RecipeService;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/users/recipes")
public class UserRecipeController {
	private UserService userService;
	private RecipeService recipeService;

	public UserRecipeController(UserService userService, RecipeService recipeService) {
		this.userService = userService;
		this.recipeService = recipeService;
	}
	
	@GetMapping
	public ResponseEntity<List<RecipeWithAttributesDto>> getAllPrivate(@RequestParam Long uid)  throws UserNotFoundException{
		if( userService.getUserById(uid).isEmpty() )
			throw new UserNotFoundException("User not found");
		return ResponseEntity.ok( recipeService.getAllIsPublic(false, uid) );
	}
	
	@PostMapping
	public ResponseEntity<RecipeWithAttributesDto> createRecipe(@Valid @RequestBody RecipeCreateDto recipeCreate) throws UserNotFoundException{
		User user = userService.getUserById( recipeCreate.getUser().getId() ).orElseThrow( () -> new UserNotFoundException("User not found") );
		return ResponseEntity.ok(recipeService.createRecipe(recipeCreate, user));
	}
}
