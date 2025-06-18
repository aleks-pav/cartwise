package lt.cartwise.product.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.mappers.ProductMapper;
import lt.cartwise.product.repositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}

	public List<ProductIngridientDto> getAll(String lng) {
		return productRepository.findAll().stream().map(p -> productMapper.toDto(p, lng)).toList();
	}

}