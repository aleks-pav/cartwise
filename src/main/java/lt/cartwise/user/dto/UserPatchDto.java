package lt.cartwise.user.dto;

import jakarta.validation.constraints.NotNull;

public class UserPatchDto {
	
	@NotNull(message = "User id field is required")
	private Long id;
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
