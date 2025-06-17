package lt.cartwise.exceptions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String, Object>> notFound(NotFoundException ex) {
		logger.debug("Vartotojas gavo NotFoundException");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<Map<String, Object>> unauthorized(InvalidRefreshTokenException ex) {
		logger.debug("Vartotojas gavo InvalidRefreshTokenException: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(createErrorBody(ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidGoogleOAuthException.class)
	public ResponseEntity<Map<String, Object>> unauthorized(InvalidGoogleOAuthException ex) {
		logger.debug("Vartotojas gavo InvalidGoogleOAuthException: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<Map<String, Object>> duplicate(DuplicateEntryException ex) {
		logger.debug("Vartotojas gavo DuplicateEntryException");

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(NoDefaultLanguageException.class)
	public ResponseEntity<Map<String, Object>> notFound(NoDefaultLanguageException ex) {
		logger.debug("Vartotojas nerastas");

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, Object>> missingParams(MissingServletRequestParameterException ex) {
		logger.debug("Vartotojas gavo MissingServletRequestParameterException klaidą");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> missingParams(MethodArgumentTypeMismatchException ex) {
		logger.debug("Vartotojas gavo MethodArgumentTypeMismatchException klaidą");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> missingParams(MethodArgumentNotValidException ex) {
		logger.debug("Validacijos klaida");

		Map<String, Object> errorBody = createErrorBody(ex.getBindingResult());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(errorBody);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> missingParams(HttpMessageNotReadableException ex) {
		logger.debug("Neįskaitomas JSON: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, Object>> inputOutput(IOException ex) {
		logger.debug("Vartotojas gavo IOException");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(createErrorBody(ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		logger.debug("Nenumatyta klaida: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(createErrorBody(ex.getMessage()));
	}
	
	

	private Map<String, Object> createErrorBody(String message) {
		Map<String, Object> error = new LinkedHashMap<>();
		error.put("error", message);
		error.put("timestamp", LocalDateTime.now());
		return error;
	}
	
	private Map<String, Object> createErrorBody(BindingResult bindingResult) {
	    Map<String, Object> error = new LinkedHashMap<>();
	    bindingResult.getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));
	    error.put("timestamp", LocalDateTime.now());
	    return error;
	}


}
