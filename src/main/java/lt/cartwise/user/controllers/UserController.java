package lt.cartwise.user.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lt.cartwise.images.ImageEditor;
import lt.cartwise.user.dto.UserDto;
import lt.cartwise.user.dto.UserPatchDto;
import lt.cartwise.user.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/me")
	public ResponseEntity<UserDto> getUserById(@AuthenticationPrincipal UserDetails userDetails){
		return ResponseEntity.of( userService.getUser(userDetails) );
	}
	
	@PatchMapping
	public ResponseEntity<UserDto> patchUser(@Valid @RequestBody UserPatchDto dto){
		return ResponseEntity.ok(userService.patchUser(dto));
	}
	
	@GetMapping("/{id}/avatar")
	public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) throws IOException{
		byte[] avatar = userService.getAvatar(id);
		String format = ImageEditor.detectImageFormat(avatar);
	    MediaType mediaType = ImageEditor.getMediaTypeForFormat(format);
		return ResponseEntity.ok().contentType( mediaType ).body(avatar);
	}
	
	@PostMapping("/{id}/avatar")
	public ResponseEntity<Void> saveAvatar(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException{
		userService.uploadAvatar(id, file);
		return ResponseEntity.ok().build();
	}
}
