package lt.cartwise.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserPatchDto {
	
	@NotNull(message = "User id field is required")
	private Long id;
	
	@Size(min = 2, message = "Name must be at least 2 symbols long")
	private String name;
	
	public UserPatchDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
