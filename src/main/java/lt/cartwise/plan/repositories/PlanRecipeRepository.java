package lt.cartwise.plan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.plan.entities.PlanRecipe;

@Repository
public interface PlanRecipeRepository extends JpaRepository<PlanRecipe, Long> {

}
