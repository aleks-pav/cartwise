package lt.cartwise.plan.services;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanRecipeCreateDto;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRecipeRepository;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.repositories.RecipeRepository;

@Service
public class PlanRecipeService {
	
	private PlanRecipeRepository planRecipeRepository;
	private PlanService planService;
	private RecipeRepository recipeRepository;
	private PlanMapper planMapper;

	public PlanRecipeService(PlanRecipeRepository planRecipeRepository
			, PlanService planService
			, RecipeRepository recipeRepository
			, PlanMapper planMapper) {
		this.planRecipeRepository = planRecipeRepository;
		this.planService = planService;
		this.recipeRepository = recipeRepository;
		this.planMapper = planMapper;
	}

	public PlanRecipeDto createPlanRecipe(@Valid PlanRecipeCreateDto dto) {
		Recipe recipe = recipeRepository.findById( dto.getRecipe_id() ).orElseThrow( () -> new NotFoundException("Recipe not found"));
		Plan plan = planService.getActiveByUser(dto.getUser_id()).orElseThrow( () -> new NotFoundException("Active plan for the user not found") );
		PlanRecipe planRecipe = new PlanRecipe();
		planRecipe.setPlan(plan);
		planRecipe.setPlanDate(dto.getPlanDate());
		planRecipe.setPortions(dto.getPortions());
		planRecipe.setRecipe(recipe);
		planRecipe.setType(dto.getType());

		return planMapper.toPlanRecipeDto( planRecipeRepository.save(planRecipe) );
	}
	
	
}

// Double portions, LocalDate planDate, MealType type, RecipeDto recipe