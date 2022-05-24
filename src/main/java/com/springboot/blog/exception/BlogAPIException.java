package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
	
	private HttpStatus status;
	private String message;
	public BlogAPIException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlogAPIException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public BlogAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
	public HttpStatus getStatus() {
		return status;
	}
	
	@Override
	public String getMessage() {
	
		return message;
	}
}
