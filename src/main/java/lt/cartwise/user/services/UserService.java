package lt.cartwise.user.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.dto.UserPatchDto;
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
	
	public Optional<UserDto> getUserDtoById(Long id) {
		return userRepository.findById(id).map( userMapper::toDto );
	}
	
	public Optional<UserDto> getUser(UserDetails userDetails) {
		return userRepository.findByEmail(userDetails.getUsername()).map( userMapper::toDto );
	}

	public UserDto patchUser(UserPatchDto dto) {
		User user = getUserById( dto.getId() ).orElseThrow( () -> new NotFoundException("User (id:"+ dto.getId() +") not  found"));
		if(dto.getName() != null)
			user.setName( dto.getName() );
		return userMapper.toDto( userRepository.save(user) );
	}

	public void uploadAvatar(Long id, MultipartFile file) throws IOException {
		User user = getUserById( id ).orElseThrow( () -> new NotFoundException("User (id:"+ id +") not  found"));
		byte[] avatar = file.getBytes();
		user.setAvatar(avatar);
		userRepository.save(user);
	}

	public byte[] getAvatar(Long id) {
		User user = getUserById( id ).orElseThrow( () -> new NotFoundException("User (id:"+ id +") not  found"));
		return user.getAvatar();
	}
	
	
	
}
