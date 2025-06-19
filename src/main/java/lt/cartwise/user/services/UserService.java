package lt.cartwise.user.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.dto.UserPatchRequest;
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

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public Optional<User> getOptional(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getOptional(UserDetails userDetails) {
		return userRepository.findByEmail(userDetails.getUsername());
	}

	public Optional<UserDto> getOptionalDto(UserDetails userDetails) {
		return getOptional(userDetails).map(userMapper::toDto);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public UserDto patchUser(UserDetails userDetails, @Valid UserPatchRequest request) {
		User user = getOptional(userDetails).orElseThrow(() -> new NotFoundException("User not found"));
		if (request.name() != null)
			user.setName(request.name());
		return userMapper.toDto(userRepository.save(user));
	}

	public void uploadAvatar(UserDetails userDetails, MultipartFile file) throws IOException {
		User user = getOptional(userDetails).orElseThrow(() -> new NotFoundException("User not found"));
		byte[] avatar = file.getBytes();
		user.setAvatar(avatar);
		userRepository.save(user);
	}

	public byte[] getAvatar(UserDetails userDetails) {
		User user = getOptional(userDetails).orElseThrow(() -> new NotFoundException("User not found"));
		return user.getAvatar();
	}

	public byte[] getAvatar(Long id) {
		User user = getOptional(id)
				.orElseThrow(() -> new NotFoundException(String.format("User (id: %s) not found", id)));
		return user.getAvatar();
	}

}
