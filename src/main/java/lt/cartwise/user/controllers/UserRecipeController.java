package lt.cartwise.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.user.services.UserRecipeService;

@RestController
@RequestMapping("/api/users/recipes")
public class UserRecipeController {
	private final UserRecipeService userRecipeService;

	public UserRecipeController(UserRecipeService userRecipeService) {
		this.userRecipeService = userRecipeService;
	}

	
	
	@GetMapping
	public ResponseEntity<List<RecipeWithAttributesDto>> getAllPrivate(@AuthenticationPrincipal UserDetails userDetails){
		return ResponseEntity.ok( userRecipeService.getAllIsPublic(false, userDetails) );
	}
	
	@GetMapping("{id}")
	public ResponseEntity<RecipeWithAttributesDto> getById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
		return ResponseEntity.of( userRecipeService.getIsPublicById(false, userDetails, id) );
	}
	
	// TODO:@Valid
	@PostMapping
	public ResponseEntity<Void> createRecipe(@AuthenticationPrincipal UserDetails userDetails
			, @RequestPart("data") String jsonString
			, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonMappingException, JsonProcessingException{
		
		RecipePostRequest recipeCreate = new ObjectMapper().readValue(jsonString, RecipePostRequest.class);
		
		userRecipeService.createRecipe(userDetails, recipeCreate, files);
		
		return ResponseEntity.accepted().build();
	}
}
