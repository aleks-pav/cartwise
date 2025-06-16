package lt.cartwise.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.aws.S3Service;
import lt.cartwise.enums.Model;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.images.ImageGalleryService;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.services.RecipeService;
import lt.cartwise.user.entities.User;

@Service
public class UserRecipeService {

	private final UserService userService;
	private final RecipeService recipeService;
	private final ImageGalleryService imageGalleryService;
	private final S3Service s3Service;

	public UserRecipeService(UserService userService, RecipeService recipeService, ImageGalleryService imageService,
			S3Service s3Service) {
		this.userService = userService;
		this.recipeService = recipeService;
		this.imageGalleryService = imageService;
		this.s3Service = s3Service;
	}

	public List<RecipeWithAttributesDto> getAllByUserDetails(UserDetails userDetails) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return recipeService.getAllByUser(userId);
	}
	
	public List<RecipeWithAttributesDto> getRecipeByUserDetails(UserDetails userDetails) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return recipeService.getAllByUser(userId);
	}
	
	public Optional<RecipeWithAttributesDto> getByIdByUserDetails(UserDetails userDetails, Long recipeId) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return recipeService.getIsPublicById(recipeId, userId);
	}

	public void deleteByIdByUserDetails(UserDetails userDetails, Long id) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		recipeService.deleteByIdByUser(id, userId);
	}
	
	@Transactional
	public void createRecipe(UserDetails userDetails, @Valid RecipePostRequest recipeCreate, List<MultipartFile> files) {
		User user = userService.getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not found"));
		
		Recipe recipe = recipeService.createRecipe(recipeCreate, user);
		
		if (files != null && !files.isEmpty()) {
			List<String> uploadedUrls = files.stream().map(s3Service::uploadFile).toList();
			imageGalleryService.createGallery(Model.RECIPE, recipe.getId(), uploadedUrls);
	    }
	}



	


	
	
}
