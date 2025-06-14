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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger( GlobalExceptionHandler.class );
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String,Object>> notFound(NotFoundException ex) {
		logger.debug("Vartotojas gavo NotFoundException");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(404).body(error);
	}
	
	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<Map<String,Object>> notFound(InvalidRefreshTokenException ex) {
		logger.debug("Vartotojas gavo InvalidRefreshTokenException: " + ex.getMessage());

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(401).body(error);
	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<Map<String,Object>> duplicate(DuplicateEntryException ex) {
		logger.debug("Vartotojas gavo DuplicateEntryException");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(409).body(error);
	}
	
	@ExceptionHandler(NoDefaultLanguageException.class)
	public ResponseEntity<Map<String,Object>> notFound(NoDefaultLanguageException ex) {
		logger.debug("Vartotojas nerastas");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(404).body(error);
	}
	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String,Object>> missingParams(MissingServletRequestParameterException ex) {
		logger.debug("Vartotojas gavo MissingServletRequestParameterException klaidą");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(400).body(error);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String,Object>> missingParams(MethodArgumentTypeMismatchException ex) {
		logger.debug("Vartotojas gavo MethodArgumentTypeMismatchException klaidą");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(400).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> missingParams(MethodArgumentNotValidException ex) {
		logger.debug("Validacijos klaida");
		
		Map<String,Object> error = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err ->
			error.put(err.getField(), err.getDefaultMessage())
	    );
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(400).body(error);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String,Object>> missingParams(HttpMessageNotReadableException ex) {
		logger.debug("Neįskaitomas JSON: " + ex.getMessage());

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(400).body(error);
	}
	
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String,Object>> inputOutput(IOException ex) {
		logger.debug("Vartotojas gavo IOException");

		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", ex.getMessage());
		error.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(400).body(error);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex) {
		String message = ex.getMessage();
		logger.debug("Nenumatyta klaida: " + message);
		
		Map<String,Object> error = new LinkedHashMap<>();
		error.put("error", message);
		error.put("timestamp", LocalDateTime.now());
		
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
