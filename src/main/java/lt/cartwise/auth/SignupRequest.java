package lt.cartwise.auth;

import jakarta.validation.constraints.*;

public record SignupRequest(
		@Email(message = "Invalid email format")
	    @NotBlank(message = "Email is required")
		String email,
		
		@Pattern(
			    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
			    message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
			)
	    String password,
	    
	    @NotBlank(message = "Name is required")
		@Size(min = 2, message = "Can't be shorter than 2 symbols")
	    String name
	    
		){}
