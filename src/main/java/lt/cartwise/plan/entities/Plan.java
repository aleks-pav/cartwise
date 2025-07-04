package lt.cartwise.plan.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.shopping.entities.ShoppingList;
import lt.cartwise.user.entities.User;

@Entity
@Table(name = "plans")
public class Plan extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Boolean isActive;

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<PlanProduct> products;

	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<PlanRecipe> recipes;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "plan", cascade = CascadeType.ALL)
	private ShoppingList shoppingList;

	public Plan() {};

	public Plan(Long id, String name, Boolean isActive, List<PlanProduct> products, List<PlanRecipe> recipes, User user,
			ShoppingList shoppingList) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.products = products;
		this.recipes = recipes;
		this.user = user;
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

	public List<PlanProduct> getProducts() {
		return products;
	}

	public void setProducts(List<PlanProduct> products) {
		this.products = products;
	}

	public List<PlanRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<PlanRecipe> recipes) {
		this.recipes = recipes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

}
