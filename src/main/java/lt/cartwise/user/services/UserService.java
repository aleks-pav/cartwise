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

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<UserDto> getUserDtoById(Long id) {
		return userRepository.findById(id).map( userMapper::toDto );
	}
	
	public Optional<UserDto> getUser(UserDetails userDetails) {
		return getUserOptional(userDetails).map( userMapper::toDto );
	}
	
	public UserDto patchUser(UserDetails userDetails, @Valid UserPatchRequest dto) {
		User user = getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not  found"));
		if(dto.name() != null)
			user.setName( dto.name() );
		return userMapper.toDto( userRepository.save(user) );
	}

	public void uploadAvatar(UserDetails userDetails, MultipartFile file) throws IOException {
		User user = getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not  found"));
		byte[] avatar = file.getBytes();
		user.setAvatar(avatar);
		userRepository.save(user);
	}
	
	public byte[] getAvatar(UserDetails userDetails) {
		User user = getUserOptional(userDetails).orElseThrow( () -> new NotFoundException("User not  found"));
		return user.getAvatar();
	}

	public byte[] getAvatar(Long id) {
		User user = getUserById( id ).orElseThrow( () -> new NotFoundException("User (id:"+ id +") not  found"));
		return user.getAvatar();
	}
	
	
	
	
	public Optional<User> getUserOptional(UserDetails userDetails){
		return userRepository.findByEmail(userDetails.getUsername());
	}
	
	public Optional<User> getUserOptional(Long id){
		return userRepository.findById(id);
	}
	
	public User saveUser(User user){
		return userRepository.save(user);
	}

	

	
	
	
	
}
