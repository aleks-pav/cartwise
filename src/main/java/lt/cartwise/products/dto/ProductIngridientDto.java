package lt.cartwise.products.dto;

import java.util.List;

import lt.cartwise.translations.TranslationByLanguageDto;

public class ProductIngridientDto {
	
	private String name;
	private Integer calories;
	private List<TranslationByLanguageDto> translations;
	
	public ProductIngridientDto(String name, Integer calories, List<TranslationByLanguageDto> translations) {
		this.name = name;
		this.calories = calories;
		this.translations = translations;
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
