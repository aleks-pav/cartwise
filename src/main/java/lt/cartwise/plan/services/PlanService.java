package lt.cartwise.plan.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.enums.Unit;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanCreateDto;
import lt.cartwise.plan.dto.PlanDeleteDto;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.entities.PlanRecipe;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRepository;
import lt.cartwise.product.entities.Product;
import lt.cartwise.recipe.entities.Ingridient;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@Service
public class PlanService {
	
	// TODO "final" missing in whole application
	private PlanRepository planRepository;
	private PlanMapper planMapper;
	private UserService userService;

	public PlanService(PlanRepository planRepository, PlanMapper planMapper, UserService userService) {
		this.planRepository = planRepository;
		this.planMapper = planMapper;
		this.userService = userService;
	}

	public List<PlanDto> getAllByUser(Long uid) {
		return planRepository.findByUserIdOrderByCreatedAtDesc(uid).stream().map(this::toPlanDto).toList();
	}
	
	public Optional<Plan> getPlan(Long id, UserDto userDto) {
		return planRepository.findByIdAndUserId(id, userDto.getId());
	}

	public Optional<PlanWithAttributesDto> getByIdByUser(Long id, Long userId) {
		Optional<Plan> optionalPlan = planRepository.findByIdAndUserId(id, userId);
		if( optionalPlan.isEmpty() )
			return Optional.empty();
		
		return Optional.of( this.toPlanWithAttributesDto( optionalPlan.get() ));
	}
	
	public PlanDto createPlan(PlanCreateDto planCreate) {
		User user = userService.getUserById( planCreate.getUserId() ).orElseThrow( () -> new NotFoundException("User not found") );
		Plan plan = this.toEntity(planCreate);
		plan.setIsActive( true );
		plan.setUser( user );
		
		deactivateAll( planCreate.getUserId() );
		
		return this.toPlanDto( planRepository.save(plan) );
	}
	
	public void deletePlan(@Valid PlanDeleteDto planDelete) {
		User user = userService.getUserById( planDelete.getUserId() ).orElseThrow( () -> new NotFoundException("User not found") );
		Plan plan = planRepository.findByIdAndUserId(planDelete.getId(), user.getId()).orElseThrow( () -> new NotFoundException("Plan with user not found") );
		planRepository.delete(plan);
	}
	
	public Optional<Plan> getActiveByUser(Long userId) {
		return planRepository.findByIsActiveAndUserId(true, userId).stream().findFirst();
	}
	
	
	
	public Map<Product, Map<Unit, Double>> calculateProducts(Long planId) {
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
	
	private void deactivateAll(Long userId) {
		List<Plan> plans = planRepository.findByIsActiveAndUserId(true, userId).stream().map( plan -> {
			plan.setIsActive(false);
			return plan;
		}).toList();
		planRepository.saveAll(plans);
	}
	
	private PlanDto toPlanDto(Plan entity) {
		return new PlanDto(entity.getId()
				, entity.getName()
				, entity.getIsActive()
				, entity.getCreatedAt()
				, entity.getUpdatedAt());
	}
	
	private PlanWithAttributesDto toPlanWithAttributesDto(Plan entity) {
		return new PlanWithAttributesDto(entity.getId()
				, entity.getName()
				, entity.getIsActive()
				, entity.getCreatedAt()
				, entity.getUpdatedAt()
				, entity.getRecipes().stream().map(planMapper::toPlanRecipeDto).toList() );
	}
	
	private Plan toEntity(PlanCreateDto dto) {
		Plan plan = new Plan();
		plan.setName( dto.getName() );
		return plan;
	}

	
	
	
}
