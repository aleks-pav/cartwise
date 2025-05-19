package lt.cartwise.recipes.entities;

import jakarta.persistence.*;
import lt.cartwise.Unit;

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
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	

	
	
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
	
	
	
}
