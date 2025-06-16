package lt.cartwise.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lt.cartwise.product.repositories.ProductRepository;

@Configuration
public class InitConfiguration {
	
	@Bean
	CommandLineRunner init(ProductRepository productRepository, DataSource dataSource) {
		return args -> {
			if( productRepository.count() < 1 ) {
				try (Connection conn = dataSource.getConnection(); InputStream inputStream = getClass().getResourceAsStream("/products.sql")) {
					if (inputStream == null) {
						throw new FileNotFoundException("SQL file not found in resources");
					}
					String sql = new String(inputStream.readAllBytes());
					Statement statement = conn.createStatement();
					for (String singleSql : sql.split(";")) {
						if (!singleSql.trim().isEmpty()) {
							statement.execute(singleSql.trim());
						}
					}
				}
			}
		};
	}
}