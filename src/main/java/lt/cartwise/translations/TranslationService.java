package lt.cartwise.translations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;

@Service
public class TranslationService {
	private TranslationRepository translationRepository;
	
	
	public TranslationService(TranslationRepository translationRepository) {
		this.translationRepository = translationRepository;
	}


	public List<TranslationByLanguageDto> getTranslationByLanguageDto(Model model, Long modelId){
		return translationRepository
				.findByTranslatableTypeAndTranslatableId(model, modelId)
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
