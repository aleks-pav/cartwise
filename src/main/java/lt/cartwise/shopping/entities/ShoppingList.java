package lt.cartwise.shopping.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.plan.entities.Plan;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@OneToOne
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;

	@OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
	private List<ShoppingListProduct> products;

	public ShoppingList() {};

	public ShoppingList(String id, Plan plan, List<ShoppingListProduct> products) {
		super();
		this.id = id;
		this.plan = plan;
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public List<ShoppingListProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ShoppingListProduct> products) {
		this.products = products;
	}

}
