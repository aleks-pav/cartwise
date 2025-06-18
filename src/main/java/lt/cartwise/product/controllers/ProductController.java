package lt.cartwise.product.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductIngridientDto>> getAll(@RequestParam String lng) {
		if (!List.of("EN", "LT", "FR", "DE").contains(lng.toUpperCase())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(productService.getAll(lng));
	}
}
