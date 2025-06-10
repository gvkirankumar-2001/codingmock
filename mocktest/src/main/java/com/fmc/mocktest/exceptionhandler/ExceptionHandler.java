package com.fmc.mocktest.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fmc.mocktest.exception.ResourceNotFoundException;
import com.fmc.mocktest.payload.CustomExceptionResponse;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<CustomExceptionResponse> globalExceptionHandler(Exception exception, WebRequest request) {
		CustomExceptionResponse response = new CustomExceptionResponse();
		response.setDate(new Date());
		response.setDetails(request.getDescription(false));
		response.setMessage(exception.getMessage());
		return new ResponseEntity<CustomExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomExceptionResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception,
			WebRequest request) {
		CustomExceptionResponse response = new CustomExceptionResponse();
		response.setDate(new Date());
		response.setDetails(request.getDescription(false));
		response.setMessage(exception.getMessage());
		return new ResponseEntity<CustomExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String message = error.getDefaultMessage();
			String field = ((FieldError) error).getField();
			errors.put(message, field);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

}
