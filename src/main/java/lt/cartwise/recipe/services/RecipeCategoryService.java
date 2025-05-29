package lt.cartwise.recipe.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;
import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.entities.RecipeCategory;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;
import lt.cartwise.translations.TranslationService;

@Service
public class RecipeCategoryService {
	
	private final RecipeCategoryRepository recipeCategoryRepository;
	private final TranslationService translationService;

	public RecipeCategoryService(RecipeCategoryRepository recipeCategoryRepository
			, TranslationService translationService) {
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.translationService = translationService;
	}

	public List<RecipeCategoryDto> getAll(String language) {
		return recipeCategoryRepository.findAll().stream().map( (c) -> this.toRecipeCategoryDto(c, language) ).toList();
	}
	
	private RecipeCategoryDto toRecipeCategoryDto(RecipeCategory category, String language) {
		return new RecipeCategoryDto(category.getId()
				, category.getName()
				, category.getSlug()
				, category.getIsActive()
				, category.getPosition()
				, translationService.getGroupedTranslations(Model.RECIPE_CATEGORY, category.getId(), language)
			);
	}
}
