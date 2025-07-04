package lt.cartwise.user.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.plan.dto.PlanWithAttributesDto;
import lt.cartwise.plan.entities.Plan;
import lt.cartwise.plan.mappers.PlanMapper;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.dto.UserResponse;
import lt.cartwise.user.entities.User;

@Component
public class UserMapper {

	private final PlanMapper planMapper;

	public UserMapper(PlanMapper planMapper) {
		this.planMapper = planMapper;
	}

	public UserDto toDto(User entity) {
		String avatarSrc = (entity.getAvatar() != null) ? "/avatars/" + entity.getId() : null;

		PlanWithAttributesDto planDto = (entity.getPlans() != null)
				? entity.getPlans().stream()
						.filter(Plan::getIsActive)
						.findFirst()
						.map(planMapper::toPlanWithAttributesDto)
						.orElse(null)
				: null;

		return new UserDto(entity.getId(), entity.getEmail(), entity.getName(), avatarSrc, entity.getRole(), planDto);
	}

	public UserResponse toUserResponse(User entity) {
		return new UserResponse(entity.getId(), entity.getEmail(), entity.getName(), entity.getRole(), entity.isActive());
	}

}
