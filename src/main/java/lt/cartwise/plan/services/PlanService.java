package lt.cartwise.plan.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lt.cartwise.enums.Unit;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanPostRequest;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRepository;
import lt.cartwise.product.entities.Product;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.shopping.services.ShoppingListService;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@Service
public class PlanService {
	
	private final PlanRepository planRepository;
	private final PlanMapper planMapper;
	private final UserService userService;
	private final ShoppingListService shoppingListService;

	public PlanService(PlanRepository planRepository
			, PlanMapper planMapper
			, UserService userService
			, ShoppingListService shoppingListService) {
		this.planRepository = planRepository;
		this.planMapper = planMapper;
		this.userService = userService;
		this.shoppingListService = shoppingListService;
	}

	public List<PlanDto> getAllByUser(UserDetails userDetails) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		return planRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(planMapper::toPlanDto).toList();
	}
	
	public Optional<PlanWithAttributesDto> getByIdByUser(UserDetails userDetails, Long id) {
		Long userId = userService.getUserOptional(userDetails).map(u -> u.getId()).orElseThrow( () -> new NotFoundException("User not found"));
		Optional<Plan> optionalPlan = planRepository.findByIdAndUserId(id, userId);
		if( optionalPlan.isEmpty() )
			return Optional.empty();
		
		return Optional.of( planMapper.toPlanWithAttributesDto( optionalPlan.get() ));
	}
	
	public PlanDto createPlan(UserDetails userDetails, @Valid PlanPostRequest planCreate) {
		User user = userService.getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not found"));
		Plan plan = planMapper.toEntity(planCreate);
		plan.setIsActive( true );
		plan.setUser( user );
		
		deactivateAll( user.getId() );
		
		return planMapper.toPlanDto( planRepository.save(plan) );
	}
	
	public void deletePlan(UserDetails userDetails, Long planId) {
		Plan plan = getPlanOptional(userDetails, planId).orElseThrow( () -> new NotFoundException("Plan with user not found") );
		planRepository.delete(plan);
	}
	
	@Transactional
	public String createShoppingList(UserDetails userDetails, Long id) {
		Plan plan = getPlanOptional(userDetails, id).orElseThrow( () -> new NotFoundException("Plan with user not found") );
		return shoppingListService.createShoppingList(plan, calculateProducts(plan.getId()));
	}
	
	public Optional<Plan> getActiveByUser(UserDetails userDetails) {
		User user = userService.getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not found"));
		return planRepository.findByIsActiveAndUserIdOrderByCreatedAtDesc(true, user.getId()).stream().findFirst();
	}
	
	
	private Map<Product, Map<Unit, Double>> calculateProducts(Long planId) {
		Plan plan = planRepository.findById(planId).orElseThrow( () -> new NotFoundException("Plan not found") );
		List<PlanRecipe> recipes = plan.getRecipes();
		
		return recipes.stream()
				.flatMap(planRecipe -> {
					Double ratio = planRecipe.getPortions() / planRecipe.getRecipe().getPortions();
					return planRecipe.getRecipe().getIngidients().stream()
							.map(ing -> {
								Ingridient scaled = new Ingridient();
								scaled.setProduct( ing.getProduct() );
								scaled.setUnits( ing.getUnits() );
								scaled.setAmount( ing.getAmount() * ratio );
								return scaled;
							});
				})
				.collect( Collectors.toMap(Ingridient::getProduct
						, ingridient -> {
							Map<Unit, Double> amounts = new HashMap<>();
							amounts.put( ingridient.getUnits(), ingridient.getAmount() );
							return amounts;
						}
						, (p1, p2) -> {
							p2.forEach((unit, amount) -> p1.merge(unit, amount, Double::sum));
							return p1;
						}));
	}
	
	public Optional<Plan> getPlanOptional(UserDetails userDetails, Long id){
		User user = userService.getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not found"));
		return planRepository.findByIdAndUserId(id, user.getId());
	}
	
	private void deactivateAll(Long userId) {
		List<Plan> plans = planRepository.findByIsActiveAndUserIdOrderByCreatedAtDesc(true, userId).stream().map( plan -> {
			plan.setIsActive(false);
			return plan;
		}).toList();
		planRepository.saveAll(plans);
	}
	
	

	

	
	
	
}
