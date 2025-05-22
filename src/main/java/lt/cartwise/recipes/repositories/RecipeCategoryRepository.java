package lt.cartwise.recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.recipes.entities.RecipeCategory;

@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
	List<RecipeCategory> findByIsActive(Boolean isActive);
}
