package lt.cartwise.user.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;

@Component
public class UserMapper {
	
	private final PlanMapper planMapper;
	
	public UserMapper(PlanMapper planMapper) {
		this.planMapper = planMapper;
	}

	public User toEntity(UserDto userDto) {
		User user = new User();
		user.setEmail( userDto.getEmail() );
		user.setName( userDto.getName() );
		user.setId( userDto.getId() );
		return user;
	}
	
	public UserDto toDto(User entity) {
		String avatarSrc = ( entity.getAvatar() != null ) ? "/avatars/" + entity.getId() : null;
		
		PlanWithAttributesDto planDto = (entity.getPlans() != null)
		        ? entity.getPlans().stream()
		            .filter(Plan::getIsActive)
		            .findFirst()
		            .map(planMapper::toPlanWithAttributesDto)
		            .orElse(null)
		        : null;
		
		return new UserDto(entity.getId()
				, entity.getEmail()
				, entity.getName()
				, avatarSrc
				, planDto);
	}
	
}
