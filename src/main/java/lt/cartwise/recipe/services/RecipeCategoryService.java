package lt.cartwise.recipe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;
import lt.cartwise.recipe.dto.RecipeCategoryDto;
import lt.cartwise.recipe.entities.RecipeCategory;
import lt.cartwise.recipe.repositories.RecipeCategoryRepository;
import lt.cartwise.translations.Translation;
import lt.cartwise.translations.TranslationByLanguageDto;
import lt.cartwise.translations.TranslationMapper;
import lt.cartwise.translations.TranslationRepository;

@Service
public class RecipeCategoryService {
	
	private RecipeCategoryRepository recipeCategoryRepository;
	private TranslationRepository translationRepository;
	private TranslationMapper translationMapper;

	public RecipeCategoryService(RecipeCategoryRepository recipeCategoryRepository
			, TranslationRepository translationRepository
			, TranslationMapper translationMapper) {
		this.recipeCategoryRepository = recipeCategoryRepository;
		this.translationRepository = translationRepository;
		this.translationMapper = translationMapper;
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
				, this.getGroupedTranslations(Model.RECIPE_CATEGORY, category.getId(), language)
			);
	}
	
	
	
	public List<TranslationByLanguageDto> getGroupedTranslations(Model model, Long id, String language) {
		List<String> languages = new ArrayList<>();
		languages.add("EN");
		languages.add(language);
		
		List<Translation> translations = translationRepository
				.findByTranslatableTypeAndTranslatableIdAndLanguageIn(model, id, languages);
		
		return translationMapper.toTranslationByLanguageDto(translations);
	}
}
