package lt.cartwise.plan.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.dto.PlanRecipePostRequest;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRecipeRepository;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.services.RecipeService;

@Service
public class PlanRecipeService {
	
	private PlanRecipeRepository planRecipeRepository;
	private PlanService planService;
	private RecipeService recipeService;
	private PlanMapper planMapper;

	public PlanRecipeService(PlanRecipeRepository planRecipeRepository
			, PlanService planService
			, RecipeService recipeService
			, PlanMapper planMapper) {
		this.planRecipeRepository = planRecipeRepository;
		this.planService = planService;
		this.recipeService = recipeService;
		this.planMapper = planMapper;
	}

	public PlanRecipeDto createPlanRecipe(UserDetails userDetails, PlanRecipePostRequest dto) {
		Recipe recipe = recipeService.getRecipeOptional( dto.recipe_id() ).orElseThrow( () -> new NotFoundException("Recipe not found"));
		Plan plan = planService.getActiveByUser(userDetails).orElseThrow( () -> new NotFoundException("Active plan for the user not found") );

		PlanRecipe planRecipe = new PlanRecipe(null
				, dto.portions()
				, dto.planDate()
				, dto.type()
				, plan
				, recipe);

		return planMapper.toPlanRecipeDto( planRecipeRepository.save(planRecipe) );
	}
	
	
}

// Double portions, LocalDate planDate, MealType type, RecipeDto recipe