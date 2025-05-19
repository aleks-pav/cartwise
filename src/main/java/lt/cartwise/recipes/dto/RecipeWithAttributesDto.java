package lt.cartwise.recipes.dto;

import java.time.LocalDateTime;
import java.util.List;

import lt.cartwise.recipes.entities.RecipeCategory;
import lt.cartwise.translations.TranslationByLanguageDto;

public class RecipeWithAttributesDto {
	
	private String name;
	private Double portions;
	private Boolean isPublic;
	private List<TranslationByLanguageDto> translations;
	private List<RecipeCategory> cetegories;
	private List<IngridientResponseDto> ingidients;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	



	public RecipeWithAttributesDto(String name, Double portions, Boolean isPublic, List<TranslationByLanguageDto> translations,
			List<IngridientResponseDto> ingidients, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.name = name;
		this.portions = portions;
		this.isPublic = isPublic;
		this.translations = translations;
		this.ingidients = ingidients;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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
	public List<RecipeCategory> getCetegories() {
		return cetegories;
	}
	public void setCetegories(List<RecipeCategory> cetegories) {
		this.cetegories = cetegories;
	}
	public List<IngridientResponseDto> getIngidients() {
		return ingidients;
	}
	public void setIngidients(List<IngridientResponseDto> ingidients) {
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
