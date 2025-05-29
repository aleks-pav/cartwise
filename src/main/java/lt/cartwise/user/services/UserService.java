package lt.cartwise.user.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.user.dto.UserCreateDto;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.mappers.UserMapper;
import lt.cartwise.user.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User createUser(@Valid UserCreateDto userCreate) {
		User user = userMapper.toEntity(userCreate);
		return userRepository.save(user);
	}
	
	
	
	
	
}
