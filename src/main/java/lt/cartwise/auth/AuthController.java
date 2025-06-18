package lt.cartwise.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;
	private final CookieService cookieService;

	public AuthController(AuthService authService, CookieService cookieService) {
		this.authService = authService;
		this.cookieService = cookieService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest request) {
		authService.signUp(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Registration successfull");
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> signUp(@Valid @RequestBody LoginRequest request,
			HttpServletResponse response) {
		AuthDto auth = authService.login(request);
		response.addHeader(HttpHeaders.SET_COOKIE,
				cookieService.createRefreshTokenCookie(auth.getRefreshToken()).toString());

		return ResponseEntity.ok(new LoginResponse(auth.getToken(), auth.getUser()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(@CookieValue(required = false) String refreshToken,
			HttpServletResponse response) {
		if (refreshToken == null || refreshToken.isBlank())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		AuthDto auth = authService.refreshToken(refreshToken);
		response.addHeader(HttpHeaders.SET_COOKIE,
				cookieService.createRefreshTokenCookie(auth.getRefreshToken()).toString());

		return ResponseEntity.ok(new LoginResponse(auth.getToken(), auth.getUser()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@CookieValue(required = false) String refreshToken,
			HttpServletResponse response) {
		if (refreshToken != null && !refreshToken.isBlank()) {
			authService.logout(refreshToken);
		}
		response.addHeader(HttpHeaders.SET_COOKIE, cookieService.expireRefreshTokenCookie().toString());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/google")
	public ResponseEntity<LoginResponse> googleLogin(@RequestBody @Valid GoogleOAuthRequest request,
			HttpServletResponse response) {
		AuthDto auth = authService.createSessionFromGoogle(request.token());
		response.addHeader(HttpHeaders.SET_COOKIE,
				cookieService.createRefreshTokenCookie(auth.getRefreshToken()).toString());

		return ResponseEntity.ok(new LoginResponse(auth.getToken(), auth.getUser()));
	}

}
