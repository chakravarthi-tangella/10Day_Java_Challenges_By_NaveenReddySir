package com.bank.customerAccountTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorDetails> AccountNotFoundException(AccountNotFoundException ex)
	{
		ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());
		return new ResponseEntity<ErrorDetails>(errorModel, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<ErrorDetails> TransactionException(TransactionException ex)
	{
		ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());
		return new ResponseEntity<ErrorDetails>(errorModel, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex)
	{
		ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());
		return new ResponseEntity<> (errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
