package lt.cartwise.user.dto;

import jakarta.validation.constraints.Size;

public record UserPatchRequest(
		@Size(min = 2, message = "Name must be at least 2 symbols long")
		String name
		
		) {}
