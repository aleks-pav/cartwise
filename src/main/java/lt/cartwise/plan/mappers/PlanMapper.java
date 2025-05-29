package lt.cartwise.plan.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.recipe.mappers.RecipeMapper;

@Component
public class PlanMapper {
	private final RecipeMapper recipeMapper;
	
	public PlanMapper(RecipeMapper recipeMapper) {
		this.recipeMapper = recipeMapper;
	}

	
	
	public PlanRecipeDto toPlanRecipeDto(PlanRecipe entity) {
		return new PlanRecipeDto(entity.getId()
				, entity.getPortions()
				, entity.getPlanDate()
				, entity.getType()
				, recipeMapper.toDto(entity.getRecipe()));
	}
}
