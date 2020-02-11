package br.com.evandrorenan.learning.clockpunch.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.evandrorenan.learning.clockpunch.utils.ViewConstants;

@ControllerAdvice
@RestController // because it provides a Response back
public class ClockPunchResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(PunchNotFoundException.class)
	public final ResponseEntity<Object> handlePunchNotFoundException(Exception ex, WebRequest request) { 
		return buildResponse(ex, HttpStatus.NOT_FOUND, request.getDescription(false));
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request)  { 
		return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponse(ex, HttpStatus.BAD_REQUEST
				, ViewConstants.VALIDATION_FAILED + ":" + ex.getBindingResult().toString());
	}

	private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus status, String description){
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), ex.getMessage(), description);
		return new ResponseEntity<>(exceptionResponse, status);
	}
}