package lt.cartwise.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.user.dto.LoginRequest;
import lt.cartwise.user.dto.LoginResponse;
import lt.cartwise.user.dto.SignupRequest;
import lt.cartwise.user.services.AuthService;

@RestController
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
}
