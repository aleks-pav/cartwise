package lt.cartwise.plan.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lt.cartwise.enums.MealType;
import lt.cartwise.enums.Unit;
import lt.cartwise.product.entities.Product;

@Entity
@Table(name = "planned_products")
public class PlanProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;
	private LocalDate planDate;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	@Enumerated(EnumType.STRING)
	private MealType type;

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public PlanProduct() {};
			
	public PlanProduct(Long id, Double amount, LocalDate planDate, Unit unit, MealType type, Plan plan,
			Product product) {
		this.id = id;
		this.amount = amount;
		this.planDate = planDate;
		this.unit = unit;
		this.type = type;
		this.plan = plan;
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public LocalDate getPlanDate() {
		return planDate;
	}

	public void setPlanDate(LocalDate planDate) {
		this.planDate = planDate;
	}

	public MealType getType() {
		return type;
	}

	public void setType(MealType type) {
		this.type = type;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
