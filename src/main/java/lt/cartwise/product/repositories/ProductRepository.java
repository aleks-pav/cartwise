package lt.cartwise.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
