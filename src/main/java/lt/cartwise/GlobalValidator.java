package lt.cartwise;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lt.cartwise.recipe.dto.RecipePostRequest;

@Component
public class GlobalValidator {

	private final Validator validator;

	public GlobalValidator(Validator validator) {
		this.validator = validator;
	}

	
	public void validate(RecipePostRequest recipePostRequest) {
		Set<ConstraintViolation<RecipePostRequest>> violations = validator.validate(recipePostRequest);
		if (!violations.isEmpty()) {
			String errorMessages = violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage())
					.collect(Collectors.joining(", "));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation failed: " + errorMessages);
		}
	}
}
