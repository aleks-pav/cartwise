package lt.cartwise.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class AuthGoogle {

	@SuppressWarnings({ "deprecation" })
	private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	private final String clientId;

	public AuthGoogle(@Value("${google.client.id}") String clientId) {
		this.clientId = clientId;
	}

	public GoogleIdToken.Payload verify(String token) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
					.setAudience(Arrays.asList(clientId)).build();

			GoogleIdToken idToken = verifier.verify(token);

			if (idToken != null) {
				return idToken.getPayload();
			} else {
				return null;
			}
		} catch (Exception ex) {
			new IOException("Bad google credentials");
			return null;
		}

	}

}
