package lt.cartwise.product.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.enums.Unit;
import lt.cartwise.recipe.entities.Ingridient;

@Entity
@Table(name = "products")
public class Product extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Integer calories;

	@Enumerated(EnumType.STRING)
	private Unit units;

	@ManyToMany
	@JoinTable(name = "products_product_categories",
		joinColumns = @JoinColumn(name = "product_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<ProductCategory> categories;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Ingridient> ingridients;

	public Product() {
	};

	public Product(Long id, String name, Integer calories, Unit units, List<ProductCategory> categories,
			List<Ingridient> ingridients) {
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.units = units;
		this.categories = categories;
		this.ingridients = ingridients;
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

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Unit getUnits() {
		return units;
	}

	public void setUnits(Unit units) {
		this.units = units;
	}

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

}
