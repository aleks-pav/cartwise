package lt.cartwise.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.recipes.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
//	List<Order> findByCustomerId(String customerId);
//	List<Order> findByCustomerEmail(String email);
//	List<Order> findByCustomerName(String name);
//	List<Order> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}
