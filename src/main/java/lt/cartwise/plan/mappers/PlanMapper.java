package lt.cartwise.plan.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanPostRequest;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.recipe.mappers.RecipeMapper;

@Component
public class PlanMapper {
	private final RecipeMapper recipeMapper;
	
	public PlanMapper(RecipeMapper recipeMapper) {
		this.recipeMapper = recipeMapper;
	}
	
	public PlanDto toPlanDto(Plan entity) {
		return new PlanDto(entity.getId()
				, entity.getName()
				, entity.getIsActive()
				, entity.getCreatedAt()
				, entity.getUpdatedAt());
	}
	
	public Plan toEntity(PlanPostRequest dto) {
		Plan plan = new Plan();
		plan.setName( dto.name() );
		return plan;
	}
	
	public PlanRecipeDto toPlanRecipeDto(PlanRecipe entity) {
		return new PlanRecipeDto(entity.getId()
				, entity.getPortions()
				, entity.getPlanDate()
				, entity.getType()
				, recipeMapper.toDto(entity.getRecipe()));
	}
	
	public PlanWithAttributesDto toPlanWithAttributesDto(Plan entity) {
		PlanWithAttributesDto dto = new PlanWithAttributesDto(entity.getId()
				, entity.getName()
				, entity.getIsActive()
				, entity.getCreatedAt()
				, entity.getUpdatedAt()
				, entity.getRecipes().stream().map(this::toPlanRecipeDto).toList());
		if( entity.getShoppingList() != null )
			dto.setShoppingList(entity.getShoppingList().getId());
		return dto;
	}
}
