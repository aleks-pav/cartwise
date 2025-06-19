package lt.cartwise.user.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.cartwise.images.ImageEditor;
import lt.cartwise.user.services.UserService;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

	private final UserService userService;

	public AvatarController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) throws IOException {
		byte[] avatar = userService.getAvatar(id);
		String format = ImageEditor.detectImageFormat(avatar);
		MediaType mediaType = ImageEditor.getMediaTypeByFormat(format);
		return ResponseEntity.ok().contentType(mediaType).body(avatar);
	}
}
