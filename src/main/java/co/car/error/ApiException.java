package co.car.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class provides centralized exception handling for the Training Center
 * application. It maps specific exceptions to appropriate HTTP status codes and
 * formats error responses in a consistent manner.
 */
@RestControllerAdvice
public class ApiException {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { BusinessException.class })
	public Map<String, String> emptyInputException(BusinessException ex) {
		Map<String, String> errMap = new HashMap<>();
		errMap.put("errorMessage", ex.getMessage());
		return errMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgs(MethodArgumentNotValidException ex) {
		Map<String, String> errMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errMap.put(error.getField(), error.getDefaultMessage());
		});
		return errMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
	public Map<String, String> handleInvalidArgs1(jakarta.validation.ConstraintViolationException ex) {
		Map<String, String> errMap = new HashMap<>();
		ex.getConstraintViolations().forEach(error -> {
			errMap.put("" + error.getPropertyPath(), error.getMessage());
		});
		return errMap;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
	public Map<String, String> handleInvalidArgs2(java.sql.SQLIntegrityConstraintViolationException ex) {
		Map<String, String> errMap = new HashMap<>();
		errMap.put("errorMessage", ex.getMessage());
		return errMap;
	}

}
