package lt.cartwise.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lt.cartwise.enums.Unit;
import lt.cartwise.product.entities.Product;
import lt.cartwise.product.repositories.ProductRepository;

@Configuration
public class InitConfiguration {

	@Bean
	CommandLineRunner init(ProductRepository productRepository) {
		return args -> {
			if( productRepository.count() < 1 ) {
				List<Product> products = List.of(
					    new Product(null, "Apple", 52, Unit.pcs, null, null),
					    new Product(null, "Banana", 89, Unit.pcs, null, null),
					    new Product(null, "Milk", 42, Unit.ml, null, null),
					    new Product(null, "Sugar", 387, Unit.g, null, null),
					    new Product(null, "Butter", 717, Unit.g, null, null),
					    new Product(null, "Olive Oil", 884, Unit.tbsp, null, null),
					    new Product(null, "Salt", 0, Unit.tsp, null, null),
					    new Product(null, "Chicken Breast", 165, Unit.g, null, null),
					    new Product(null, "Egg", 68, Unit.pcs, null, null),
					    new Product(null, "Honey", 304, Unit.tbsp, null, null)
					);
				productRepository.saveAll(products);
			}
		};
		
	}
}
