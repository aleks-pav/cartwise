package lt.cartwise.recipes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.recipes.dto.IngridientResponseDto;
import lt.cartwise.recipes.dto.RecipeWithAttributesDto;
import lt.cartwise.recipes.entities.Ingridient;
import lt.cartwise.recipes.entities.Recipe;
import lt.cartwise.recipes.repositories.RecipeRepository;

@Service
public class RecipeService {
	
	private RecipeRepository recipeRepository;

	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public List<RecipeWithAttributesDto> getAll() {
		return recipeRepository.findAll().stream().map( this::toRecipeWithAttributesDto ).toList();
	}
	
	
	
	
	
	
	
	private RecipeWithAttributesDto toRecipeWithAttributesDto(Recipe recipe) {
		return new RecipeWithAttributesDto(recipe.getName()
					, recipe.getPortions()
					, recipe.getIsPublic()
					, recipe.getIngidients().stream().map( this::toIngridientResponseDto ).toList()
					, recipe.getCreatedAt()
					, recipe.getUpdatedAt()
				);
	}
	
	private IngridientResponseDto toIngridientResponseDto(Ingridient item) {
		return new IngridientResponseDto(item.getAmount(), item.getUnits());
	}
	
}
