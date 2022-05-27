package com.springboot.blog.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Api(value = "Comment model information")
@Data
public class CommentDTO {
	
	
	@ApiModelProperty(value = "Comment id")
	private Long id;
	
	@ApiModelProperty(value = "Comment name")
	//nome should not be null or empty
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@ApiModelProperty(value = "Comment email")
	//email field validation
	@NotEmpty(message = "Email should not be null or empty")
	@Email(message = "Email format is not correct")
	private String email;
	
	@ApiModelProperty(value = "Comment body")
	@NotEmpty
	@Size(min = 10, message = "Coment body must be minimum at least 10 characters")
	private String body;

}
