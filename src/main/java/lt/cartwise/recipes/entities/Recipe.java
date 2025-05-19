package lt.cartwise.recipes.entities;

import java.util.List;

import jakarta.persistence.*;
import lt.cartwise.Timestampable;


@Entity
@Table(name = "recipes")
public class Recipe extends Timestampable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Double portions;
	private Boolean isPublic;
	
	@ManyToMany(mappedBy = "recipes")
	private List<RecipeCategory> cetegories;

	@OneToMany (mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Ingridient> ingidients;
	
	
	
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

	public Double getPortions() {
		return portions;
	}

	public void setPortions(Double portions) {
		this.portions = portions;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<RecipeCategory> getCetegories() {
		return cetegories;
	}

	public void setCetegories(List<RecipeCategory> cetegories) {
		this.cetegories = cetegories;
	}

	public List<Ingridient> getIngidients() {
		return ingidients;
	}

	public void setIngidients(List<Ingridient> ingidients) {
		this.ingidients = ingidients;
	}
	
	
	
}
