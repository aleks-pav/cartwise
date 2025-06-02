package lt.cartwise.plan.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PlanWithAttributesDto {
	
	private Long id;
	private String name;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	private List<PlanRecipeDto> recipes;
//	private List<PlanProductDto> products;
	
	private String shoppingList;

	public PlanWithAttributesDto(Long id
			, String name
			, Boolean isActive
			, LocalDateTime createdAt
			, LocalDateTime updatedAt
			, List<PlanRecipeDto> recipes
			, String shoppingList) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.recipes = recipes;
		this.shoppingList = shoppingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<PlanRecipeDto> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<PlanRecipeDto> recipes) {
		this.recipes = recipes;
	}

	public String getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(String shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	
}
