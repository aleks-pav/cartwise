package lt.cartwise.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	private final CookieService cookieService;

	public AuthController(AuthService authService, CookieService cookieService) {
		this.authService = authService;
		this.cookieService = cookieService;
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest request){
		authService.signUp(request);
		return ResponseEntity.ok("Registration successfull");
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> signUp(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
		AuthDto auth = authService.login(request);
		response.addHeader(HttpHeaders.SET_COOKIE, cookieService.createRefreshTokenCookie(auth.getRefreshToken()).toString());
		return ResponseEntity.ok( new LoginResponse(auth.getToken(), auth.getUser()) );
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(@CookieValue String refreshToken, HttpServletResponse response) {
		AuthDto auth = authService.refreshToken(refreshToken);
		response.addHeader(HttpHeaders.SET_COOKIE, cookieService.createRefreshTokenCookie(auth.getRefreshToken()).toString());
	    return ResponseEntity.ok( new LoginResponse(auth.getToken(), auth.getUser()) );
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
	    response.addHeader(HttpHeaders.SET_COOKIE, cookieService.expireRefreshTokenCookie().toString());
	    return ResponseEntity.noContent().build();
	}

}
