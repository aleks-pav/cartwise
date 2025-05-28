package lt.cartwise.plan.dto;

import java.time.LocalDate;

import lt.cartwise.enums.MealType;
import lt.cartwise.recipe.dto.RecipeDto;

public class PlanRecipeDto {

	private Long id;
	
	private Double portions;
	private LocalDate planDate;
	private MealType type;
	private RecipeDto recipe;

	public PlanRecipeDto(Long id, Double portions, LocalDate planDate, MealType type, RecipeDto recipe) {
		this.id = id;
		this.portions = portions;
		this.planDate = planDate;
		this.type = type;
		this.recipe = recipe;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPortions() {
		return portions;
	}

	public void setPortions(Double portions) {
		this.portions = portions;
	}

	public LocalDate getPlanDate() {
		return planDate;
	}

	public void setPlanDate(LocalDate planDate) {
		this.planDate = planDate;
	}

	public MealType getType() {
		return type;
	}

	public void setType(MealType type) {
		this.type = type;
	}

	public RecipeDto getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeDto recipe) {
		this.recipe = recipe;
	}
	
	
}
