package com.springboot.blog.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class CommentDTO {
	
	private Long id;
	
	//nome should not be null or empty
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	//email field validation
	@NotEmpty(message = "Email should not be null or empty")
	@Email(message = "Email format is not correct")
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Coment body must be minimum at least 10 characters")
	private String body;

}
