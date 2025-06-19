package lt.cartwise.product.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.enums.Model;
import lt.cartwise.product.dto.ProductDto;
import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.entities.Product;
import lt.cartwise.translations.TranslationService;

@Component
public class ProductMapper {

	private final TranslationService translationService;

	public ProductMapper(TranslationService translationService) {
		this.translationService = translationService;
	}

	public ProductDto toDto(Product entity) {
		return new ProductDto(entity.getId(), entity.getName(),
				translationService.getGroupedTranslations(Model.PRODUCT, entity.getId()));
	}
	
	public ProductIngridientDto toDto(Product entity, String lng) {
		return new ProductIngridientDto(
				entity.getId(),
				entity.getName(),
				entity.getCalories(),
				translationService.getGroupedTranslations(Model.PRODUCT, entity.getId(), lng));
	}
	
	public ProductIngridientDto toProductIngridientDto(Product product) {
		return new ProductIngridientDto(product.getId(), product.getName(), product.getCalories(),
				translationService.getGroupedTranslations(Model.PRODUCT, product.getId()));
	}
}
