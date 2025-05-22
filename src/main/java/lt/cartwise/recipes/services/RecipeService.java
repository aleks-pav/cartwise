package lt.cartwise.recipes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;
import lt.cartwise.products.dto.ProductIngridientDto;
import lt.cartwise.products.entities.Product;
import lt.cartwise.recipes.dto.RecipeCategoriesDto;
import lt.cartwise.recipes.dto.RecipeIngridientsDto;
import lt.cartwise.recipes.dto.RecipeWithAttributesDto;
import lt.cartwise.recipes.entities.Ingridient;
import lt.cartwise.recipes.entities.Recipe;
import lt.cartwise.recipes.entities.RecipeCategory;
import lt.cartwise.recipes.repositories.RecipeRepository;
import lt.cartwise.translations.Translation;
import lt.cartwise.translations.TranslationByLanguageDto;
import lt.cartwise.translations.TranslationMapper;
import lt.cartwise.translations.TranslationRepository;

@Service
public class RecipeService {
	
	private RecipeRepository recipeRepository;
	private TranslationRepository translationRepository;
	private TranslationMapper translationMapper;

	public RecipeService(RecipeRepository recipeRepository, TranslationRepository translationRepository, TranslationMapper translationMapper) {
		this.recipeRepository = recipeRepository;
		this.translationRepository = translationRepository;
		this.translationMapper = translationMapper;
	}

	public List<RecipeWithAttributesDto> getAllPublic() {
		return recipeRepository.findByIsPublic(true).stream().map( this::toRecipeWithAttributesDto ).toList();
	}
	
	public boolean deleteById(Long id) {
		if( !recipeRepository.existsById(id) )
			return false;
		recipeRepository.deleteById(id);
		return true;
	}
	
	
	
	
	
	private RecipeWithAttributesDto toRecipeWithAttributesDto(Recipe recipe) {
		return new RecipeWithAttributesDto(recipe.getId()
					, recipe.getName()
					, recipe.getPortions()
					, recipe.getIsPublic()
					, this.getGroupedTranslations( Model.RECIPE, recipe.getId() )
					, recipe.getIngidients().stream().map( this::toRecipeIngridientsDto ).toList()
					, recipe.getCategories().stream().map( this::toRecipeCategoriesDto ).toList()
					, recipe.getCreatedAt()
					, recipe.getUpdatedAt()
				);
	}
	
	
	private RecipeIngridientsDto toRecipeIngridientsDto(Ingridient item) {
		return new RecipeIngridientsDto(item.getAmount()
				, item.getUnits()
				, this.toProductIngridientDto(item.getProduct())
			);
	}
	
	private RecipeCategoriesDto toRecipeCategoriesDto(RecipeCategory cat) {
		return new RecipeCategoriesDto(cat.getId()
				, cat.getName()
				, cat.getSlug()
				, cat.getIsActive()
			);
	}
	
	private ProductIngridientDto toProductIngridientDto(Product product) {
		return new ProductIngridientDto(product.getName()
				, product.getCalories()
				, this.getGroupedTranslations( Model.PRODUCT, product.getId() )
			);
	}

	public List<TranslationByLanguageDto> getGroupedTranslations(Model model, Long id) {
		List<Translation> translations = translationRepository
				.findByTranslatableTypeAndTranslatableId(model, id);
		
		return translationMapper.toTranslationByLanguageDto(translations);
	}

	
	
	
}
