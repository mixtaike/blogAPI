package com.springboot.blog.dtos;

import lombok.Data;

@Data
public class LoginDTO {
	
	private String usernameOrEmail;
	private String password;
	
	
	
}
