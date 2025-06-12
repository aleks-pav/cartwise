package lt.cartwise.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}



	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest request){
		authService.signUp(request);
		return ResponseEntity.ok("Registration successfull");
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> signUp(@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok( authService.login(request) );
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
	    return ResponseEntity.ok( authService.refreshToken(request.token()) );
	}

}
