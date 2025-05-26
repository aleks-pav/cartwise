package lt.cartwise.recipe.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;

@Entity
@Table(
		name = "recipe_categories"
		, indexes = {@Index(name = "slug_lookup", columnList = "slug")}
)
public class RecipeCategory extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String slug;
	private Boolean isActive;
	private Integer position;
	
	@ManyToMany(mappedBy = "categories")
	private List<Recipe> recipes;
	
	
	
	

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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	

	
	
}
