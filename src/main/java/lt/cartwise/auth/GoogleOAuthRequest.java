package lt.cartwise.auth;

import jakarta.validation.constraints.NotBlank;

public record GoogleOAuthRequest(
		@NotBlank(message = "Token is required")
		String token) {

}
