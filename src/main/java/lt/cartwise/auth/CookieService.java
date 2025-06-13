package lt.cartwise.auth;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieService {
	@Value("${app.env}")
    private String appEnv;
	
	public ResponseCookie createRefreshTokenCookie(String token) {
		boolean isProduction = appEnv.equalsIgnoreCase("production");
		return ResponseCookie
				.from("refreshToken", token)
				.httpOnly(true)
				.secure(isProduction)
				.path("/api/auth/refresh")
				.maxAge(Duration.ofDays(7))
				.sameSite("Strict")
				.build();
	}
	
	public ResponseCookie expireRefreshTokenCookie() {
		boolean isProduction = appEnv.equalsIgnoreCase("production");
		return ResponseCookie
				.from("refreshToken", "")
				.httpOnly(true)
				.secure(isProduction)
				.path("/api/auth/refresh")
				.maxAge(Duration.ofDays(0))
				.sameSite("Strict")
				.build();
	}
	
}
