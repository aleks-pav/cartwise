package lt.cartwise.plan.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.user.entities.User;

@Repository
public interface PlanRecipeRepository extends JpaRepository<PlanRecipe, Long> {
	Optional<PlanRecipe> findByIdAndPlan_User(Long id, User user);

}
