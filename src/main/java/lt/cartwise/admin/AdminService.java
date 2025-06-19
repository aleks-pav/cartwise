package lt.cartwise.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.recipe.dto.RecipePatchRequest;
import lt.cartwise.recipe.dto.RecipeResponse;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.mappers.RecipeMapper;
import lt.cartwise.recipe.services.RecipeService;
import lt.cartwise.user.dto.UserPutRequest;
import lt.cartwise.user.dto.UserResponse;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.mappers.UserMapper;
import lt.cartwise.user.services.UserService;

@Service
public class AdminService {

	private final UserService userService;
	private final UserMapper userMapper;
	private final RecipeService recipeService;
	private final RecipeMapper recipeMapper;

	public AdminService(UserService userService, UserMapper userMapper, RecipeService recipeService,
			RecipeMapper recipeMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.recipeService = recipeService;
		this.recipeMapper = recipeMapper;
	}

	public List<UserResponse> getUsersList() {
		return userService.getAll().stream().map(userMapper::toUserResponse).toList();
	}

	public UserResponse putUser(@Valid UserPutRequest request) {
		User user = userService.getOptional(request.id())
				.orElseThrow(() -> new NotFoundException("User not found"));
		user.setActive(request.isActive());
		user.setRole(request.role());
		return userMapper.toUserResponse(userService.saveUser(user));
	}

	public List<RecipeResponse> getRecipesList() {
		return recipeService.getAllByIsPublic(true).stream().map(recipeMapper::toRecipeResponse).toList();
	}

	public RecipeResponse patchRecipe(@Valid RecipePatchRequest request) {
		Recipe recipe = recipeService.getOptionalById(request.id())
				.orElseThrow(() -> new NotFoundException("Recipe not found"));
		recipe.setIsVerified(request.isVerified());
		return recipeMapper.toRecipeResponse(recipeService.saveRecipe(recipe));
	}

}
