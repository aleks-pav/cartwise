package lt.cartwise.plan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanRecipeDto;
import lt.cartwise.plan.dto.PlanRecipePatchRequest;
import lt.cartwise.plan.dto.PlanRecipePostRequest;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRecipeRepository;
import lt.cartwise.recipe.entities.Recipe;
import lt.cartwise.recipe.services.RecipeService;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@Service
public class PlanRecipeService {

	private final PlanRecipeRepository planRecipeRepository;
	private final PlanService planService;
	private final RecipeService recipeService;
	private final UserService userService;
	private final PlanMapper planMapper;

	public PlanRecipeService(PlanRecipeRepository planRecipeRepository, PlanService planService,
			RecipeService recipeService, UserService userService, PlanMapper planMapper) {
		this.planRecipeRepository = planRecipeRepository;
		this.planService = planService;
		this.recipeService = recipeService;
		this.userService = userService;
		this.planMapper = planMapper;
	}

	public void createPlanRecipe(UserDetails userDetails, List<@Valid PlanRecipePostRequest> dtos) {
		List<PlanRecipe> planRecipes = dtos.stream().map(dto -> {
			Recipe recipe = recipeService.getRecipeOptional(dto.recipe_id())
					.orElseThrow(() -> new NotFoundException("Recipe not found"));
			Plan plan = planService.getActiveByUser(userDetails)
					.orElseThrow(() -> new NotFoundException("Active plan for the user not found"));
			return new PlanRecipe(null, dto.portions(), dto.planDate(), dto.type(), plan, recipe);
		}).toList();

		planRecipeRepository.saveAll(planRecipes);
	}

	public void deletePlanRecipe(UserDetails userDetails, Long id) {
		PlanRecipe recipePlan = planRecipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Plan recipe not found"));
		planService.getPlanOptional(userDetails, recipePlan.getPlan().getId()).ifPresentOrElse(
				p -> planRecipeRepository.delete(recipePlan),
				() -> new NotFoundException("Plan recipe for the user not found"));
	} 

	public PlanRecipeDto putPlanRecipe(UserDetails userDetails, @Valid PlanRecipePatchRequest dto) {
		User user = userService.getUserOptional(userDetails).orElseThrow(() -> new NotFoundException("User not found"));
		PlanRecipe recipePlan = planRecipeRepository.findByIdAndPlan_User(dto.id(), user)
				.orElseThrow(() -> new NotFoundException("Plan recipe not found"));

		Optional.ofNullable(dto.portions()).ifPresent(recipePlan::setPortions);
		Optional.ofNullable(dto.planDate()).ifPresent(recipePlan::setPlanDate);
		Optional.ofNullable(dto.type()).ifPresent(recipePlan::setType);

		return planMapper.toPlanRecipeDto(planRecipeRepository.save(recipePlan));
	}

}