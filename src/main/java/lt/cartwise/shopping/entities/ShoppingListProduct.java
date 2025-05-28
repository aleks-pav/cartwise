package lt.cartwise.shopping.entities;

import jakarta.persistence.*;
import lt.cartwise.enums.Unit;
import lt.cartwise.product.entities.Product;

@Entity
@Table(name = "shopping_list_products")
public class ShoppingListProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double amount;
	private Unit units;
	private Boolean isCompleted;
	
	@ManyToOne
	@JoinColumn(name = "shopping_list_id", nullable = false)
	private ShoppingList shoppingList;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	
	
	
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

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
