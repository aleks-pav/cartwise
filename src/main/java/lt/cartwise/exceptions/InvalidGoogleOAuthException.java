package lt.cartwise.exceptions;

public class InvalidGoogleOAuthException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidGoogleOAuthException(String message) {
		super(message);
	}
}