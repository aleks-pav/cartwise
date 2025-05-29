package lt.cartwise.translations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;
import lt.cartwise.exceptions.NoDefaultLanguageException;

@Service
public class TranslationService {
	private final TranslationRepository translationRepository;
	private final TranslationMapper translationMapper;
	
	
	public TranslationService(TranslationRepository translationRepository, TranslationMapper translationMapper) {
		this.translationRepository = translationRepository;
		this.translationMapper = translationMapper;
	}
	
	
	public List<Translation> createTraslations(Model model, Long id, List<Translation> translationsInput) {
		List<Translation> translations = translationsInput.stream().map( t -> {
			Translation translation = new Translation();
			translation.setTranslatableType(model);
			translation.setTranslatableId(id);
			translation.setLanguage( t.getLanguage().toUpperCase() );
			translation.setFiledName( t.getFieldName().toLowerCase() );
			translation.setValue( t.getValue() );
			return translation;
		}).toList();
		
		return translationRepository.saveAll( translations );
	}
	
	public List<Translation> createTraslationsWithFallback(Model model, Long id, List<Translation> translationsInput) {
		boolean hasEnglish = translationsInput.stream().anyMatch( t -> t.getLanguage().equals("EN"));
		if(!hasEnglish)
			throw new NoDefaultLanguageException("There is no default language");
		
		List<Translation> translations = translationsInput.stream().map( t -> {
			Translation translation = new Translation();
			translation.setTranslatableType(model);
			translation.setTranslatableId(id);
			translation.setLanguage( t.getLanguage().toUpperCase() );
			translation.setFiledName( t.getFieldName().toLowerCase() );
			translation.setValue( t.getValue() );
			return translation;
		}).toList();
		
		return translationRepository.saveAll( translations );
	}
	
	public List<TranslationByLanguageDto> getGroupedTranslations(Model model, Long id) {
		List<Translation> translations = translationRepository
				.findByTranslatableTypeAndTranslatableId(model, id);
		
		return translationMapper.toTranslationByLanguageDto(translations);
	}
	
	public List<TranslationByLanguageDto> getGroupedTranslations(Model model, Long id, String language) {
		List<String> languages = new ArrayList<>();
		languages.add("EN");
		languages.add(language);
		
		List<Translation> translations = translationRepository
				.findByTranslatableTypeAndTranslatableIdAndLanguageIn(model, id, languages);
		
		return translationMapper.toTranslationByLanguageDto(translations);
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
