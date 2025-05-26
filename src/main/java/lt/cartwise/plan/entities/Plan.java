package lt.cartwise.plan.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.user.entities.User;

@Entity
@Table(name = "plans")
public class Plan extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<PlanProduct> products;
	
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
	public List<PlanProduct> getProducts() {
		return products;
	}
	public void setProducts(List<PlanProduct> products) {
		this.products = products;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
