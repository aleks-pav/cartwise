package lt.cartwise.recipe.dto;

public class RecipeCategoriesDto {
	private Long id;
	private String name;
	private String slug;
	private Boolean isActive;
	
	
	
	public RecipeCategoriesDto(Long id, String name, String slug, Boolean isActive) {
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.isActive = isActive;
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
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
