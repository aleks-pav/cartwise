package lt.cartwise.recipe.entities;

import jakarta.persistence.*;
import lt.cartwise.enums.Unit;
import lt.cartwise.product.entities.Product;

@Entity
@Table(name = "ingridients")
public class Ingridient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	@Enumerated(EnumType.STRING)
	private Unit units;

	@ManyToOne
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public Ingridient() {};

	public Ingridient(Long id, Double amount, Unit units, Recipe recipe, Product product) {
		this.id = id;
		this.amount = amount;
		this.units = units;
		this.recipe = recipe;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Unit getUnits() {
		return units;
	}

	public void setUnits(Unit units) {
		this.units = units;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
