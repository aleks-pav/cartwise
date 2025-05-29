package lt.cartwise.plan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.cartwise.plan.entities.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {
	List<Plan> findByUserIdOrderByCreatedAtDesc(Long userId);
	List<Plan> findByIsActiveAndUserId(Boolean isActive, Long userId);
	Optional<Plan> findByIdAndUserId(Long id, Long userId);
}
