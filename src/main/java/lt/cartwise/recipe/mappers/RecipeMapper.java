package lt.cartwise.recipe.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.enums.Model;
import lt.cartwise.images.ImageGalleryService;
import lt.cartwise.product.mappers.ProductMapper;
import lt.cartwise.recipe.dto.RecipeCategoriesDto;
import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.dto.RecipeDto;
import lt.cartwise.recipe.dto.RecipeIngridientDto;
import lt.cartwise.recipe.dto.RecipeResponse;
import lt.cartwise.recipe.dto.RecipeWithAttributesDto;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.entities.RecipeCategory;
import lt.cartwise.translations.TranslationService;

@Component
public class RecipeMapper {

	private final TranslationService translationService;
	private final ImageGalleryService imageGalleryService;
	private final ProductMapper productMapper;

	public RecipeMapper(TranslationService translationService, ImageGalleryService imageGalleryService,
			ProductMapper productMapper) {
		this.translationService = translationService;
		this.imageGalleryService = imageGalleryService;
		this.productMapper = productMapper;
	}

	public RecipeWithAttributesDto toRecipeWithAttributesDto(Recipe recipe) {
		return new RecipeWithAttributesDto(recipe.getId(), recipe.getName(), recipe.getPortions(),
				recipe.getTimePreparation(), recipe.getTimeCooking(), recipe.getIsPublic(),
				translationService.getGroupedTranslations(Model.RECIPE, recipe.getId()),
				recipe.getCategories().stream().map(this::toRecipeCategoriesDto).toList(),
				recipe.getIngidients().stream().map(this::toRecipeIngridientsDto).toList(),
				imageGalleryService.getActiveByType(Model.RECIPE, recipe.getId()), recipe.getCreatedAt(),
				recipe.getUpdatedAt());
	}

	public RecipeDto toDto(Recipe entity) {
		return new RecipeDto(entity.getId(), entity.getName(),
				translationService.getGroupedTranslations(Model.RECIPE, entity.getId()));
	}

	public RecipeResponse toRecipeResponse(Recipe entity) {
		return new RecipeResponse(entity.getId(), entity.getName(), entity.getIsVerified(), entity.getCreatedAt());
	}

	public RecipeCategoryDto toRecipeCategoryDto(RecipeCategory category, String language) {
		return new RecipeCategoryDto(category.getId(), category.getName(), category.getSlug(), category.getIsActive(),
				category.getPosition(),
				translationService.getGroupedTranslations(Model.RECIPE_CATEGORY, category.getId(), language));
	}

	private RecipeIngridientDto toRecipeIngridientsDto(Ingridient item) {
		return new RecipeIngridientDto(item.getAmount(), item.getUnits(),
				productMapper.toProductIngridientDto(item.getProduct()));
	}

	private RecipeCategoriesDto toRecipeCategoriesDto(RecipeCategory cat) {
		return new RecipeCategoriesDto(cat.getId(), cat.getName(), cat.getSlug(), cat.getIsActive());
	}

}