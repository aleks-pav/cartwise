package lt.cartwise.translations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

	@Transactional
	public List<Translation> createTraslations(Model model, Long id, List<Translation> translationsInput) {
		List<Translation> translations = translationsInput.stream().map(t -> {
			return new Translation(null,
					id,
					model,
					t.getLanguage().toUpperCase(),
					t.getFieldName().toLowerCase(),
					t.getValue());
		}).toList();

		return translationRepository.saveAll(translations);
	}

	public List<Translation> createTraslationsWithFallback(Model model, Long id, List<Translation> translationsInput) {
		boolean hasEnglish = translationsInput.stream().anyMatch(t -> t.getLanguage().equals("EN"));
		if (!hasEnglish)
			throw new NoDefaultLanguageException("There is no default language");

		List<Translation> translations = translationsInput.stream().map(t -> {
			return new Translation(null,
					id,
					model,
					t.getLanguage().toUpperCase(),
					t.getFieldName().toLowerCase(),
					t.getValue());
		}).toList();

		return translationRepository.saveAll(translations);
	}

	public List<TranslationByLanguageDto> getGroupedTranslations(Model model, Long id) {
		List<Translation> translations = translationRepository.findByTranslatableTypeAndTranslatableId(model, id);

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
}
