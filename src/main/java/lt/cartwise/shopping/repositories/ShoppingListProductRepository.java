package lt.cartwise.shopping.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.shopping.entities.ShoppingListProduct;

@Repository
public interface ShoppingListProductRepository extends JpaRepository<ShoppingListProduct, Long> {

	Optional<ShoppingListProduct> findByIdAndShoppingListId(Long id, String shoppingListId);

}
