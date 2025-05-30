package lt.cartwise.plan.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lt.cartwise.enums.MealType;

public class PlanRecipeCreateDto {

	@NotNull(message = "Portions amount required")
	private Double portions;
	private LocalDate planDate;
	private MealType type;
	
	@NotNull(message = "Recipe id is required")
	private Long recipe_id;
	
	@NotNull(message = "User id is required")
	private Long user_id;

	public PlanRecipeCreateDto(Double portions, LocalDate planDate, MealType type, Long recipe_id, Long user_id) {
		this.portions = portions;
		this.planDate = planDate;
		this.type = type;
		this.recipe_id = recipe_id;
		this.user_id = user_id;
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

	public Long getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(Long recipe_id) {
		this.recipe_id = recipe_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	
	
	
}
