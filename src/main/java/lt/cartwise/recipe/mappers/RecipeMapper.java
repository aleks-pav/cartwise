package lt.cartwise.recipe.mappers;


import org.springframework.stereotype.Component;

import lt.cartwise.enums.Model;
import lt.cartwise.recipe.dto.RecipeCreateDto;
import lt.cartwise.recipe.dto.RecipeDto;
import lt.cartwise.recipe.dto.RecipePostRequest;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.translations.TranslationService;

@Component
public class RecipeMapper {
	
	private final TranslationService translationService;
	
	public RecipeMapper(TranslationService translationService) {
		this.translationService = translationService;
	}

	
	public Recipe toEntity(RecipePostRequest dto) {
		Recipe entity = new Recipe();
		entity.setName( dto.name() );
		entity.setPortions( dto.portions() );
		entity.setTimePreparation( dto.timePreparation() );
		entity.setTimeCooking( dto.timeCooking() );
		entity.setIsPublic( dto.isPublic() );
//		TODO handle User, Categories and Ingridients
//		entity.setUser( user );
//		entity.setCategories( dto.getCategories().stream().map( this::toRecipeCategoryEntity ).toList() );
//		entity.setIngidients( dto.getIngidients().stream().map( this::toIngridientEntity ).toList() );
		
		return entity;
	}
	
	public Recipe toEntity(RecipeCreateDto dto) {
		Recipe entity = new Recipe();
		entity.setName( dto.getName() );
		entity.setPortions( dto.getPortions() );
		entity.setTimePreparation( dto.getTimePreparation() );
		entity.setTimeCooking( dto.getTimeCooking() );
		entity.setIsPublic( dto.getIsPublic() );
//		TODO handle User, Categories and Ingridients
//		entity.setUser( user );
//		entity.setCategories( dto.getCategories().stream().map( this::toRecipeCategoryEntity ).toList() );
//		entity.setIngidients( dto.getIngidients().stream().map( this::toIngridientEntity ).toList() );
		
		return entity;
	}
	
	public RecipeDto toDto(Recipe entity) {
		return new RecipeDto(entity.getId(), entity.getName(), translationService.getGroupedTranslations( Model.RECIPE, entity.getId() ));
	}
	
	public RecipeDto toDto(Recipe entity, String language) {
		return new RecipeDto(entity.getId(), entity.getName(), translationService.getGroupedTranslations( Model.RECIPE, entity.getId(), language ));
	}
}