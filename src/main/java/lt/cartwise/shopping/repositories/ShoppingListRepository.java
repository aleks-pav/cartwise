package lt.cartwise.shopping.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.cartwise.shopping.entities.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, String> {
	Optional<ShoppingList> findByPlanId(Long planId);
}
