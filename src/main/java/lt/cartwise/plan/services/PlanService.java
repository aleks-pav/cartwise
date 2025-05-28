package lt.cartwise.plan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.plan.dto.PlanCreateDto;
import lt.cartwise.plan.dto.PlanDto;
import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.plan.repositories.PlanRepository;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@Service
public class PlanService {
	
	private PlanRepository planRepository;
	private PlanMapper planMapper;
	private UserService userService;

	public PlanService(PlanRepository planRepository, PlanMapper planMapper, UserService userService) {
		this.planRepository = planRepository;
		this.planMapper = planMapper;
		this.userService = userService;
	}

	public List<PlanDto> getAllByUser(Long uid) {
		return planRepository.findByUserId(uid).stream().map(this::toPlanDto).toList();
	}

	public Optional<PlanWithAttributesDto> getByIdByUser(Long id, UserDto userDto) {
		Optional<Plan> optionalPlan = planRepository.findByIdAndUserId(id, userDto.getId());
		if( optionalPlan.isEmpty() )
			return Optional.empty();
		
		return Optional.of( this.toPlanWithAttributesDto( optionalPlan.get() ));
	}
	
	public PlanDto createPlan(@Valid PlanCreateDto planCreate, UserDto userDto) throws NotFoundException {
		User user = userService.getUserById( userDto.getId() ).orElseThrow( () -> new NotFoundException("User not found") );
		Plan plan = this.toEntity(planCreate);
		plan.setIsActive( true );
		plan.setUser( user );
		
		deactivateAll( userDto.getId() );
		
		return this.toPlanDto( planRepository.save(plan) );
	}
	
	public Optional<Plan> getActiveByUser(Long userId) {
		return planRepository.findByIsActiveAndUserId(true, userId).stream().findFirst();
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
