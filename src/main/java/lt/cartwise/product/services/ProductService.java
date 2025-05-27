package lt.cartwise.product.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.enums.Model;
import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.entities.Product;
import lt.cartwise.product.repositories.ProductRepository;
import lt.cartwise.translations.TranslationService;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	private TranslationService translationService;

	public ProductService(ProductRepository productRepository, TranslationService translationService) {
		this.productRepository = productRepository;
		this.translationService = translationService;
	}

	public List<ProductIngridientDto> getAll(String lng) {
		return productRepository.findAll().stream().map( p -> this.toDto(p, lng) ).toList();
	}
	
	
	
	
	private ProductIngridientDto toDto(Product entity, String lng) {
		return new ProductIngridientDto(entity.getId()
				, entity.getName()
				, entity.getCalories()
				, translationService.getGroupedTranslations(Model.PRODUCT, entity.getId(), lng));
	}
}