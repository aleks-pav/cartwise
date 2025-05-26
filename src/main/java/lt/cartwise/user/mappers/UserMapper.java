package lt.cartwise.user.mappers;

import org.springframework.stereotype.Component;

import lt.cartwise.user.dto.UserCreateDto;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.entities.User;

@Component
public class UserMapper {
	
	public User toEntity(UserCreateDto userCreate) {
		User user = new User();
		user.setEmail( userCreate.getEmail() );
		user.setName( userCreate.getName() );
		user.setPassword( userCreate.getPassword() );
		return user;
	}
	
	public User toEntity(UserDto userDto) {
		User user = new User();
		user.setEmail( userDto.getEmail() );
		user.setName( userDto.getName() );
		user.setId( userDto.getId() );
		return user;
	}
}
