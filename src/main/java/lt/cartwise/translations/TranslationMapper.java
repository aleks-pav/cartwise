package lt.cartwise.translations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TranslationMapper {
	
	
	
	public List<TranslationByLanguageDto> toTranslationByLanguageDto(List<Translation> translations){
		return translations
				.stream()
				.collect( Collectors.groupingBy( Translation::getLanguage ))
				.entrySet()
				.stream()
				.map(entry -> {
					Map<String, String> fields = entry
							.getValue()
							.stream()
							.collect( Collectors.toMap(Translation::getFieldName, Translation::getValue) );
					return new TranslationByLanguageDto(entry.getKey(), fields);
				})
				.toList();
	}
}
