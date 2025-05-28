package lt.cartwise.product.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.enums.Model;
import lt.cartwise.product.dto.ProductDto;
import lt.cartwise.product.entities.Product;
import lt.cartwise.translations.TranslationService;

@Component
public class ProductMapper {

	private TranslationService translationService;

	public ProductMapper(TranslationService translationService) {
		this.translationService = translationService;
	}
	
	public ProductDto toDto(Product entity) {
		return new ProductDto(entity.getId()
				, entity.getName()
				, translationService.getGroupedTranslations(Model.PRODUCT, entity.getId()));
	}
}
