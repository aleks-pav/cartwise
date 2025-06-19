package lt.cartwise.translations;

import java.util.Map;

public class TranslationByLanguageDto {

	private String language;
	private Map<String, String> fields;

	public TranslationByLanguageDto(String language, Map<String, String> fields) {
		this.language = language;
		this.fields = fields;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

}
