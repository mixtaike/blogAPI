package com.springboot.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.dtos.ErrorDetails;


//tratar excecoes de forma global
@ControllerAdvice
public class GlobalExceptionHandler {
	
	//handle specific exceptions
	//global exceptions
	
	@ExceptionHandler(ResourceNotFoundException.class)//anotação usada para tratar uma exceção especifica
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		exception.printStackTrace();
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAPIException.class)//anotação usada para tratar uma exceção especifica
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		exception.printStackTrace();
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	//global exception
	
	@ExceptionHandler(Exception.class)//anotação usada para tratar uma exceção especifica
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		exception.printStackTrace();
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
