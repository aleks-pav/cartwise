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
import lt.cartwise.user.dto.UserPatchRequest;
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
	public ResponseEntity<UserDto> patchUser(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserPatchRequest dto){
		return ResponseEntity.ok(userService.patchUser(userDetails, dto));
	}
	
	@PostMapping("/avatar")
	public ResponseEntity<Void> saveAvatar(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MultipartFile file) throws IOException{
		userService.uploadAvatar(userDetails, file);
		return ResponseEntity.ok().build();
	}
	
	// TODO: is this endpoint redundant?
	@GetMapping("/avatar")
	public ResponseEntity<byte[]> getAvatar(@AuthenticationPrincipal UserDetails userDetails) throws IOException{
		byte[] avatar = userService.getAvatar(userDetails);
		String format = ImageEditor.detectImageFormat(avatar);
	    MediaType mediaType = ImageEditor.getMediaTypeForFormat(format);
		return ResponseEntity.ok().contentType( mediaType ).body(avatar);
	}
	
	
}
