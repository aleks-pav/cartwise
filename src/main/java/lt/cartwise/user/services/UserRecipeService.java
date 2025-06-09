package lt.cartwise.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.aws.S3Service;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.images.ImageService;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.services.RecipeService;
import lt.cartwise.user.entities.User;

@Service
public class UserRecipeService {

	private final UserService userService;
	private final RecipeService recipeService;
	private final ImageService imageService;
	private final S3Service s3Service;

	public UserRecipeService(UserService userService, RecipeService recipeService, ImageService imageService,
			S3Service s3Service) {
		this.userService = userService;
		this.recipeService = recipeService;
		this.imageService = imageService;
		this.s3Service = s3Service;
	}

	
	public List<RecipeWithAttributesDto> getAllIsPublic(boolean isPublic, UserDetails userDetails) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return recipeService.getAllIsPublic(isPublic, userId);
	}

	public Optional<RecipeWithAttributesDto> getIsPublicById(boolean isPublic, UserDetails userDetails, Long recipeId) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return recipeService.getIsPublicById(isPublic, recipeId, userId);
	}

	@Transactional
	public RecipeWithAttributesDto createRecipe(UserDetails userDetails, @Valid RecipePostRequest recipeCreate, List<MultipartFile> files) {
		User user = userService.getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not found"));
		
		if (files != null && !files.isEmpty()) {
			List<String> uploadedUrls = files.stream().map(s3Service::uploadFile).toList();
	    }
		
		return recipeService.createRecipe(recipeCreate, user);
		
	}


	
	
}
