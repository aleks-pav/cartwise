package lt.cartwise.product.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.cartwise.product.dto.ProductIngridientDto;
import lt.cartwise.product.services.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<ProductIngridientDto>> getAll(@RequestParam String lng){
		return ResponseEntity.ok( productService.getAll(lng) );
	}
}
