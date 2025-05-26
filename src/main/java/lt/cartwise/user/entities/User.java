package lt.cartwise.user.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.recipe.entities.Recipe;

@Entity
@Table(name = "users"
		, uniqueConstraints  = {@UniqueConstraint(name = "user_unique", columnNames = {"email"})}
)
public class User extends Timestampable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String name;
	private String password;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] avatar;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Recipe> recipes;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Plan> plans;
	
	
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
	
	
	
}
