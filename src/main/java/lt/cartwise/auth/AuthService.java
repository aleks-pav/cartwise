package lt.cartwise.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lt.cartwise.enums.Role;
import lt.cartwise.exceptions.DuplicateEntryException;
import lt.cartwise.exceptions.NotFoundException;
import lt.cartwise.exceptions.InvalidRefreshTokenException;
import lt.cartwise.security.JwtUtils;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.mappers.UserMapper;
import lt.cartwise.user.repositories.UserRepository;

@Service
public class AuthService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthService(UserRepository userRepository,
			UserMapper userMapper,
			RefreshTokenRepository refreshTokenRepository,
			PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,
			JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.refreshTokenRepository = refreshTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	public void signUp(@Valid SignupRequest request) {
		if (userRepository.findByEmail(request.email()).isPresent())
			throw new DuplicateEntryException(String.format("User with email %s already exists", request.email()));
		User user = new User(null, request.email(), request.name(), passwordEncoder.encode(request.password()), true,
				Role.USER, null, null, null);
		userRepository.save(user);
	}

	public AuthDto login(@Valid LoginRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		User user = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new NotFoundException("User not found"));

		return new AuthDto(jwtUtils.generateToken(authentication.getName()), createRefreshToken(user).getToken(),
				userMapper.toDto(user));
	}

	public AuthDto refreshToken(String oldToken) {
		RefreshToken newToken = rotateToken(oldToken);
		User user = newToken.getUser();

		return new AuthDto(jwtUtils.generateToken(user.getEmail()), newToken.getToken(), userMapper.toDto(user));
	}

	private RefreshToken createRefreshToken(User user) {
		Instant now = Instant.now();
		String token = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(null, token, user, now.plusMillis(7 * 24 * 60 * 60 * 1000)); // 7-days

		return refreshTokenRepository.save(refreshToken);
	}

	private RefreshToken rotateToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

		if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
			throw new InvalidRefreshTokenException("Refresh token expired");
		}
		User user = refreshToken.getUser();
		refreshTokenRepository.delete(refreshToken);

		return createRefreshToken(user);
	}
}
