package lt.cartwise.user.dto;

import jakarta.validation.constraints.*;

public class UserCreateDto {
	
	@Email
	private String email;
	
	@Size(min = 2, message = "Can't be shorter than 2 symbols")
	private String name;
	
	@Pattern(
		    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
		    message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
		)
	private String password;
	
	
	public UserCreateDto(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
