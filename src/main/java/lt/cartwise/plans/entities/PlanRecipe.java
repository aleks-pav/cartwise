package lt.cartwise.plans.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lt.cartwise.enums.MealType;
import lt.cartwise.recipes.entities.Recipe;

@Entity
@Table(name = "planned_recipes")
public class PlanRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double portions;
	private LocalDate planDate;
	
	@Enumerated(EnumType.STRING)
	private MealType type;
	
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	
	
	
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
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
