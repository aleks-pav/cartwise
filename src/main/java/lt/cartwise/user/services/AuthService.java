package lt.cartwise.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.enums.Role;
import lt.cartwise.exceptions.DuplicateEntryException;
import lt.cartwise.security.JwtUtils;
import lt.cartwise.user.dto.LoginRequest;
import lt.cartwise.user.dto.LoginResponse;
import lt.cartwise.user.dto.SignupRequest;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.repositories.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	public void signUp(@Valid SignupRequest request) {
		if( userRepository.findByEmail( request.email() ).isPresent() )
			throw new DuplicateEntryException("User with email " + request.email() + " already exists");
		User user = new User(null, request.email(), request.name(), passwordEncoder.encode(request.password()), true, Role.USER, null, null, null);
		userRepository.save(user);
	}

	public LoginResponse login(@Valid LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		return new LoginResponse( jwtUtils.generateToken( authentication.getName() ) ); 
	}

	
	
	
}
