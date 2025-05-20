package lt.cartwise.plans.entity;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;

@Entity
@Table(name = "plans")
public class Plan extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<PlanProduct> products;
	

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
}
