package lt.cartwise.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private SecretKey secretKey;
	private long expirationMillis;
	
	public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationMillis) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMillis = expirationMillis;
	}

	public String generateToken(String email) {
		Instant now = Instant.now();
		return Jwts.builder()
				.subject(email)
				.issuedAt( Date.from(now) )
				.expiration(Date.from(now.plusMillis(expirationMillis)))
				.signWith(secretKey)
				.compact();
	}
	
	public String extractEmail(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean validateToken(String token, String email) {
		try {
			String extracredEmail = extractEmail(token);
			return extracredEmail.equals(email) && !isTokenExpired(token);
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
		return expiration.before(new Date());
	}
}
