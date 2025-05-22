package lt.cartwise.translations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.cartwise.enums.Model;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
	List<Translation> findByTranslatableTypeAndTranslatableId(Model translatableType, Long translatableId);
	List<Translation> findByTranslatableTypeAndTranslatableIdAndLanguageIn(Model translatableType, Long translatableId, List<String> languages);
}
