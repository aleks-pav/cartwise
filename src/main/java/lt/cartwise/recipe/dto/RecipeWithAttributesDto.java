package lt.cartwise.recipe.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lt.cartwise.images.ImageResponse;
import lt.cartwise.translations.TranslationByLanguageDto;

public class RecipeWithAttributesDto {

	private Long id;
	private String name;
	private Integer portions;
	private Integer timePreparation;
	private Integer timeCooking;
	private Boolean isPublic;
	private List<TranslationByLanguageDto> translations;
	private List<RecipeCategoriesDto> categories;
	private List<RecipeIngridientDto> ingidients;
	private Map<String, List<ImageResponse>> galleries;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public RecipeWithAttributesDto(Long id,
			String name,
			Integer portions,
			Integer timePreparation,
			Integer timeCooking,
			Boolean isPublic,
			List<TranslationByLanguageDto> translations,
			List<RecipeCategoriesDto> categories,
			List<RecipeIngridientDto> ingidients,
			Map<String, List<ImageResponse>> galleries,
			LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.portions = portions;
		this.timePreparation = timePreparation;
		this.timeCooking = timeCooking;
		this.isPublic = isPublic;
		this.translations = translations;
		this.categories = categories;
		this.ingidients = ingidients;
		this.galleries = galleries;
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

	public Integer getPortions() {
		return portions;
	}

	public void setPortions(Integer portions) {
		this.portions = portions;
	}

	public Integer getTimePreparation() {
		return timePreparation;
	}

	public void setTimePreparation(Integer timePreparation) {
		this.timePreparation = timePreparation;
	}

	public Integer getTimeCooking() {
		return timeCooking;
	}

	public void setTimeCooking(Integer timeCooking) {
		this.timeCooking = timeCooking;
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

	public List<RecipeIngridientDto> getIngidients() {
		return ingidients;
	}

	public void setIngidients(List<RecipeIngridientDto> ingidients) {
		this.ingidients = ingidients;
	}

	public Map<String, List<ImageResponse>> getGalleries() {
		return galleries;
	}

	public void setGalleries(Map<String, List<ImageResponse>> galleries) {
		this.galleries = galleries;
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
