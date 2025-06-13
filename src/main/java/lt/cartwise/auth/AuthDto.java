package lt.cartwise.auth;

import lt.cartwise.user.dto.UserDto;

public class AuthDto {
	
	private String token;
	private String refreshToken;
	private UserDto user;
	
	public AuthDto(String token, String refreshToken, UserDto user) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.user = user;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	
	
	
}
