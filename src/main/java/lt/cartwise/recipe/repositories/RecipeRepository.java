package lt.cartwise.recipe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.recipe.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> findByUserId(Long userId);
	List<Recipe> findByIsPublic(Boolean isPublic);
	List<Recipe> findByIsPublicAndUserId(Boolean isPublic, Long userId);
	Optional<Recipe> findByIdAndIsPublic(Long id, Boolean isPublic);
	Optional<Recipe> findByIdAndIsPublicAndUserId(Long id, Boolean isPublic, Long userId);
	Optional<Recipe> findByIdAndUserId(Long id, Long userId);
}
