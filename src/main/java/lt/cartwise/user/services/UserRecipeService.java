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
		Long userId = userService.getOptional(userDetails).map(User::getId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return recipeService.getAllByUserId(userId);
	}

	public Optional<RecipeWithAttributesDto> getByIdUserDetails(Long recipeId, UserDetails userDetails) {
		Long userId = userService.getOptional(userDetails).map(User::getId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return recipeService.getOptionalDtoByIdUsedId(recipeId, userId);
	}

	public void deleteByIdUserDetails(Long id, UserDetails userDetails) {
		Long userId = userService.getOptional(userDetails).map(User::getId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		recipeService.deleteByIdUserId(id, userId);
	}

	@Transactional
	public void createRecipe(UserDetails userDetails, @Valid RecipePostRequest recipeCreate,
			List<MultipartFile> files) {

		User user = userService.getOptional(userDetails).orElseThrow(() -> new NotFoundException("User not found"));

		Recipe recipe = recipeService.createRecipe(recipeCreate, user);

		if (files != null && !files.isEmpty()) {
			List<String> uploadedUrls = files.stream().map(s3Service::uploadFile).toList();
			imageGalleryService.createGallery(Model.RECIPE, recipe.getId(), uploadedUrls);
		}
	}

}
