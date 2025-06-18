package lt.cartwise.user.dto;

import jakarta.validation.constraints.*;
import lt.cartwise.enums.Role;

public record UserPutRequest(
		
		@NotNull(message = "User id is required")
		@Positive(message = "User id must be a positive integer")
		Long id,
		
		@NotNull(message = "User isActive required")
		Boolean isActive,
		
		@NotNull(message = "User role is required")
		Role role
		) {}