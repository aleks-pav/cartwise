package lt.cartwise.products.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.enums.Unit;
import lt.cartwise.recipes.entities.Ingridient;

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
	
	@ManyToMany(mappedBy = "products")
	private List<ProductCategory> categories;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Ingridient> ingridients;
	
	
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
