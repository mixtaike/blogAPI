package com.springboot.blog.dtos;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDTOV2 {
	
private Long id;
	
	
	// title should not be null or empty
	// title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	//post description should not be null or empty
	//post description should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	//post content should not be null or empty
	@NotEmpty
	private String content;
	
	private Set<CommentDTO> comments;
	private List<String> tags;
	

}
