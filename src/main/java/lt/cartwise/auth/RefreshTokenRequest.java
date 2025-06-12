package lt.cartwise.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
		@NotBlank(message = "Refresh token is required")
		String token
		
		) {}
