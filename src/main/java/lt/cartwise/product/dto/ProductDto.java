package lt.cartwise.product.dto;

import java.util.List;

import lt.cartwise.translations.TranslationByLanguageDto;

public class ProductDto {

	private Long id;
	private String name;
	private List<TranslationByLanguageDto> translations;

	public ProductDto(Long id, String name, List<TranslationByLanguageDto> translations) {
		this.id = id;
		this.name = name;
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

	public List<TranslationByLanguageDto> getTranslations() {
		return translations;
	}

	public void setTranslations(List<TranslationByLanguageDto> translations) {
		this.translations = translations;
	}

}
