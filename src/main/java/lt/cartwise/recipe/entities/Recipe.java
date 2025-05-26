package lt.cartwise.recipe.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.user.entities.User;


@Entity
@Table(name = "recipes")
public class Recipe extends Timestampable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Integer portions;
	private Integer timePreparation = 0;
	private Integer timeCooking = 0;
	private Boolean isPublic;
	
	
	@ManyToMany
	@JoinTable(name = "recipes_recipe_categories"
			,joinColumns = @JoinColumn(name = "recipe_id")
			, inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private List<RecipeCategory> categories;

	@OneToMany (mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Ingridient> ingidients;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
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

	public Integer getPortions() {
		return portions;
	}

	public void setPortions(Integer portions) {
		this.portions = portions;
	}

	public Integer getTimePreparation() {
		return timePreparation;
	}

	public void setTimePreparation(Integer timePreparation) {
		this.timePreparation = timePreparation;
	}

	public Integer getTimeCooking() {
		return timeCooking;
	}

	public void setTimeCooking(Integer timeCooking) {
		this.timeCooking = timeCooking;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<RecipeCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<RecipeCategory> categories) {
		this.categories = categories;
	}

	public List<Ingridient> getIngidients() {
		return ingidients;
	}

	public void setIngidients(List<Ingridient> ingidients) {
		this.ingidients = ingidients;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
