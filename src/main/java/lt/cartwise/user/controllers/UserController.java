package lt.cartwise.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lt.cartwise.user.dto.UserCreateDto;
import lt.cartwise.user.entities.User;
import lt.cartwise.user.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		return ResponseEntity.of( userService.getUserById(id) );
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDto userCreate){
		return ResponseEntity.ok(userService.createUser(userCreate));
	}
}
