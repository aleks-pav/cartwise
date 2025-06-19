package lt.cartwise.recipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.enums.Model;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.product.entities.Product;
import lt.cartwise.product.repositories.ProductRepository;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.mappers.RecipeMapper;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;
import lt.cartwise.recipe.repositories.RecipeRepository;
import lt.cartwise.translations.TranslationService;
import lt.cartwise.user.entities.User;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCategoryRepository recipeCategoryRepository;
	private final ProductRepository productRepository;
	private final RecipeMapper recipeMapper;
	private final TranslationService translationService;

	public RecipeService(RecipeRepository recipeRepository, RecipeCategoryRepository recipeCategoryRepository,
			ProductRepository productRepository, RecipeMapper recipeMapper, TranslationService translationService) {
		this.recipeRepository = recipeRepository;
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.productRepository = productRepository;
		this.recipeMapper = recipeMapper;
		this.translationService = translationService;
	}

	public List<RecipeWithAttributesDto> getAllByUserId(Long userId) {
		return recipeRepository.findByUserId(userId).stream().map(recipeMapper::toRecipeWithAttributesDto).toList();
	}

	public List<RecipeWithAttributesDto> getAllByIsPublicIsVerified(boolean isPublic, boolean isVerified) {
		return recipeRepository.findByIsPublicAndIsVerified(isPublic, isVerified).stream()
				.map(recipeMapper::toRecipeWithAttributesDto).toList();
	}

	public List<Recipe> getAllByIsPublic(boolean isPublic) {
		return recipeRepository.findByIsPublic(isPublic);
	}

	public Optional<RecipeWithAttributesDto> getOptionalDtoByIdIsPublic(boolean isPublic, Long id) {
		return recipeRepository.findByIdAndIsPublicAndIsVerified(id, true, true)
				.map(recipeMapper::toRecipeWithAttributesDto);
	}

	public Optional<RecipeWithAttributesDto> getOptionalDtoByIdUsedId(Long id, Long userId) {
		return recipeRepository.findByIdAndUserId(id, userId).map(recipeMapper::toRecipeWithAttributesDto);
	}

	public void deleteByIdUserId(Long id, Long userId) {
		recipeRepository.findByIdAndUserId(id, userId).ifPresentOrElse(recipeRepository::delete,
				() -> new NotFoundException(String.format("Recipe (id: %s) by user not found", id)));
	}

	@Transactional
	public Recipe createRecipe(@Valid RecipePostRequest recipeCreate, User user) {
		Recipe recipe = new Recipe(null,
				recipeCreate.name(),
				recipeCreate.portions(),
				recipeCreate.timePreparation(),
				recipeCreate.timeCooking(),
				recipeCreate.isPublic(),
				false,
				recipeCategoryRepository.findAllById(recipeCreate.categories()),
				null,
				user);

		List<Ingridient> ingridients = recipeCreate.ingridients().stream().map(ingridientDto -> {
			Long productId = ingridientDto.productId();
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new NotFoundException(String.format("Product (id: %s) not found", productId)));
			return new Ingridient(null, ingridientDto.amount(), ingridientDto.units(), recipe, product);
		}).toList();

		recipe.setIngidients(ingridients);

		Recipe recipeNew = recipeRepository.save(recipe);
		translationService.createTraslations(Model.RECIPE, recipeNew.getId(), recipeCreate.translations());

		return recipeNew;
	}

	public Optional<Recipe> getOptionalById(Long id) {
		return recipeRepository.findById(id);
	}

	public Recipe saveRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

}
