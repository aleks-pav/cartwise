package lt.cartwise.user.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;

@Component
public class UserMapper {
	

	public User toEntity(UserDto userDto) {
		User user = new User();
		user.setEmail( userDto.getEmail() );
		user.setName( userDto.getName() );
		user.setId( userDto.getId() );
		return user;
	}
	
	public UserDto toDto(User entity) {
		String avatarSrc = ( entity.getAvatar() != null ) ? "/avatars/" + entity.getId() : null;
		return new UserDto(entity.getId()
				, entity.getEmail()
				, entity.getName()
				, avatarSrc);
	}
}
