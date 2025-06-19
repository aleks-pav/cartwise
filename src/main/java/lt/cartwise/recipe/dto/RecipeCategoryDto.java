package lt.cartwise.recipe.dto;

import java.util.List;

import lt.cartwise.translations.TranslationByLanguageDto;

public class RecipeCategoryDto {
	private Long id;
	private String name;
	private String slug;
	private Boolean isActive;
	private Integer position;
	private List<TranslationByLanguageDto> translations;

	public RecipeCategoryDto(Long id, String name, String slug, Boolean isActive, Integer position,
			List<TranslationByLanguageDto> translations) {
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.isActive = isActive;
		this.position = position;
		this.translations = translations;
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<TranslationByLanguageDto> getTranslations() {
		return translations;
	}

	public void setTranslations(List<TranslationByLanguageDto> translations) {
		this.translations = translations;
	}
}
