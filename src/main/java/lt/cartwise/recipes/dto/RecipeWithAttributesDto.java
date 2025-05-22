package lt.cartwise.recipes.dto;

import java.time.LocalDateTime;
import java.util.List;

import lt.cartwise.translations.TranslationByLanguageDto;

public class RecipeWithAttributesDto {
	
	private Long id;
	private String name;
	private Double portions;
	private Boolean isPublic;
	private List<TranslationByLanguageDto> translations;
	private List<RecipeCategoriesDto> categories;
	private List<RecipeIngridientsDto> ingidients;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	



	public RecipeWithAttributesDto(Long id, String name, Double portions, Boolean isPublic, List<TranslationByLanguageDto> translations,
			List<RecipeIngridientsDto> ingidients, List<RecipeCategoriesDto> categories, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.portions = portions;
		this.isPublic = isPublic;
		this.translations = translations;
		this.ingidients = ingidients;
		this.categories = categories;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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
	public List<TranslationByLanguageDto> getTranslations() {
		return translations;
	}
	public void setTranslations(List<TranslationByLanguageDto> translations) {
		this.translations = translations;
	}
	public List<RecipeCategoriesDto> getCategories() {
		return categories;
	}
	public void setCategories(List<RecipeCategoriesDto> categories) {
		this.categories = categories;
	}
	public List<RecipeIngridientsDto> getIngidients() {
		return ingidients;
	}
	public void setIngidients(List<RecipeIngridientsDto> ingidients) {
		this.ingidients = ingidients;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	
}
