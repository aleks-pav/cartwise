package lt.cartwise.recipe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.recipe.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> findByIsPublic(Boolean isPublic);
	Optional<Recipe> findByIdAndIsPublic(Long id, Boolean isPublic);
}
