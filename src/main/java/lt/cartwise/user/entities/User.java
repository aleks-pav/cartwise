package lt.cartwise.user.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.enums.Role;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.recipe.entities.Recipe;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "user_unique", columnNames = { "email" }) })
public class User extends Timestampable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;
	private String name;
	
	@Column(nullable = false)
	private String password;
	private boolean isActive;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] avatar;

	@OneToMany(mappedBy = "user")
	private List<Recipe> recipes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Plan> plans;

	public User() {};

	public User(Long id, String email, String name, String password, boolean isActive, Role role, byte[] avatar,
			List<Recipe> recipes, List<Plan> plans) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.isActive = isActive;
		this.role = role;
		this.avatar = avatar;
		this.recipes = recipes;
		this.plans = plans;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

}
