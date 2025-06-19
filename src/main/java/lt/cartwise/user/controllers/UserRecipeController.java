package lt.cartwise.user.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.cartwise.GlobalValidator;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.user.services.UserRecipeService;

@RestController
@RequestMapping("/api/users/recipes")
public class UserRecipeController {

	private final UserRecipeService userRecipeService;
	private final GlobalValidator globalValidator;

	public UserRecipeController(UserRecipeService userRecipeService, GlobalValidator globalValidator) {
		this.userRecipeService = userRecipeService;
		this.globalValidator = globalValidator;
	}

	@GetMapping
	public ResponseEntity<List<RecipeWithAttributesDto>> getAllByUserDetails(
			@AuthenticationPrincipal UserDetails userDetails) {
		return ResponseEntity.ok(userRecipeService.getAllByUserDetails(userDetails));
	}

	@GetMapping("{id}")
	public ResponseEntity<RecipeWithAttributesDto> getByIdUserDetails(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id) {
		return ResponseEntity.of(userRecipeService.getByIdUserDetails(id, userDetails));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long id) {
		userRecipeService.deleteByIdUserDetails(id, userDetails);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@PostMapping
	public ResponseEntity<Void> createRecipe(@AuthenticationPrincipal UserDetails userDetails,
			@RequestPart("data") String jsonString,
			@RequestPart(value = "files", required = false) List<MultipartFile> files)
			throws JsonMappingException, JsonProcessingException {

		RecipePostRequest recipeCreate = new ObjectMapper().readValue(jsonString, RecipePostRequest.class);
		globalValidator.validate(recipeCreate);
		userRecipeService.createRecipe(userDetails, recipeCreate, files);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

}
