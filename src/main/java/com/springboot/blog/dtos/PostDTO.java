package com.springboot.blog.dtos;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Post model information")
@Data
public class PostDTO {
	
	
	
	@ApiModelProperty(value = "Blog post id")
	private Long id;
	
	@ApiModelProperty(value = "Blog post title")
	// title should not be null or empty
	// title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	@ApiModelProperty(value = "Blog post description")
	//post description should not be null or empty
	//post description should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	@ApiModelProperty(value = "Blog post content")
	//post content should not be null or empty
	@NotEmpty
	private String content;
	
	@ApiModelProperty(value = "Blog post comments")
	private Set<CommentDTO> comments;
	
	

}
