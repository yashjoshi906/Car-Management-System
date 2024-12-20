package co.car.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ExceptionErrorResponse  {
private final String message;
private final Throwable throwable;
private final HttpStatus httpStatus;


}
