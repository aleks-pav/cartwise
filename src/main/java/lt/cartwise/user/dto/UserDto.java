package lt.cartwise.user.dto;

public class UserDto {
	
	private Long id;
	private String email;
	private String name;
	private String avatarSrc;
	
	
	public UserDto(Long id) {
		this.id = id;
	}
	
	public UserDto(Long id, String email, String name, String avatarSrc) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.avatarSrc = avatarSrc;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getAvatarSrc() {
		return avatarSrc;
	}
	public void setAvatarSrc(String avatarSrc) {
		this.avatarSrc = avatarSrc;
	}
	
	
	
}
