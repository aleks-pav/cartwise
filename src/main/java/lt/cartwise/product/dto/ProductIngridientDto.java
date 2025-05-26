package lt.cartwise.product.dto;

import java.util.List;

import lt.cartwise.translations.TranslationByLanguageDto;

public class ProductIngridientDto {
	
	private Long id;
	private String name;
	private Integer calories;
	private List<TranslationByLanguageDto> translations;
	
	public ProductIngridientDto(Long id, String name, Integer calories, List<TranslationByLanguageDto> translations) {
		this.id = id;
		this.name = name;
		this.calories = calories;
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
	public Integer getCalories() {
		return calories;
	}
	public void setCalories(Integer calories) {
		this.calories = calories;
	}
	public List<TranslationByLanguageDto> getTranslations() {
		return translations;
	}
	public void setTranslations(List<TranslationByLanguageDto> translations) {
		this.translations = translations;
	}
	
	
}
