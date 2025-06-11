package lt.cartwise.plan.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanRecipePostRequest;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.repositories.PlanRecipeRepository;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.services.RecipeService;

@Service
public class PlanRecipeService {
	
	private PlanRecipeRepository planRecipeRepository;
	private PlanService planService;
	private RecipeService recipeService;

	public PlanRecipeService(PlanRecipeRepository planRecipeRepository
			, PlanService planService
			, RecipeService recipeService) {
		this.planRecipeRepository = planRecipeRepository;
		this.planService = planService;
		this.recipeService = recipeService;
	}

	public void createPlanRecipe(UserDetails userDetails, List<PlanRecipePostRequest> dtos) {
		List<PlanRecipe> planRecipes = dtos.stream().map(dto -> {
			Recipe recipe = recipeService.getRecipeOptional( dto.recipe_id() ).orElseThrow( () -> new NotFoundException("Recipe not found"));
			Plan plan = planService.getActiveByUser(userDetails).orElseThrow( () -> new NotFoundException("Active plan for the user not found") );
			return new PlanRecipe(null
					, dto.portions()
					, dto.planDate()
					, dto.type()
					, plan
					, recipe);
		}).toList();

		planRecipeRepository.saveAll(planRecipes);
	}
	
	
}