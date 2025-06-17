package lt.cartwise.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.validation.Valid;
import lt.cartwise.enums.Role;
import lt.cartwise.exceptions.DuplicateEntryException;
import lt.cartwise.exceptions.InvalidGoogleOAuthException;
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
	private final AuthGoogle authGoogle;

	public AuthService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
			UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
			JwtUtils jwtUtils, AuthGoogle authGoogle) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.authGoogle = authGoogle;
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

	public void logout(String refreshToken) {
		refreshTokenRepository.findByToken(refreshToken).ifPresent(refreshTokenRepository::delete);
	}

	public AuthDto refreshToken(String oldToken) {
		RefreshToken newToken = rotateToken(oldToken);
		User user = newToken.getUser();

		return new AuthDto(jwtUtils.generateToken(user.getEmail()), newToken.getToken(), userMapper.toDto(user));
	}

	public AuthDto createSessionFromGoogle(String token) {

		GoogleIdToken.Payload payload = Optional.ofNullable(authGoogle.verify(token))
				.orElseThrow(() -> new InvalidGoogleOAuthException("Invalid google token"));

		String email = payload.getEmail();
		String name = (String) payload.get("name");
		//TODO: replace dummyPassword
		String dummyPassword = passwordEncoder.encode("oauth-user");
		
		User user = userRepository.findByEmail(email).orElseGet(() -> {
			User newUser = new User(null, email, name, dummyPassword, true, Role.USER, null, null, null);
			return userRepository.save(newUser);
		});

		return new AuthDto(jwtUtils.generateToken(user.getEmail()), createRefreshToken(user).getToken(),
				userMapper.toDto(user));
	}

	private RefreshToken createRefreshToken(User user) {
		Instant now = Instant.now();
		String token = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(null, token, user, now.plusMillis(7 * 24 * 60 * 60 * 1000)); // 7-days

		return refreshTokenRepository.save(refreshToken);
	}

	public RefreshToken rotateToken(String token) {
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
