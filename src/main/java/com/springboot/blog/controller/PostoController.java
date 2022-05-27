package com.springboot.blog.controller;



import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.PostDTO;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@Api(value = "CRUS Rest Apis for Post resources" )
@RestController
@RequestMapping()
public class PostoController {
	
	private PostService postService;

	public PostoController(PostService postService) {
		super();
		this.postService = postService;
	}

	
	@ApiOperation(value = "Create Post REST API")
	
	// create blog post api
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("api/v1/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}
	
	//get all posts rest api
	@ApiOperation(value = "Get All Posts REST API")
	@GetMapping("api/v1/posts")
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo, 
			@RequestParam(value = "pageSize", defaultValue =AppConstants.DEFAULT_PAGE_SIZES, required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			){
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	//get post by id 
	@ApiOperation(value = "Create Post By Id REST API")
	@GetMapping(value = "api/v1/posts/{id}")
	public ResponseEntity<PostDTO> getPostByIdV1(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	

	@ApiOperation(value = "Update Post By Id REST API")
	//update post by id rest apoi
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("api/v1/posts/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable int id ) {
		PostDTO postResponse=  postService.updatePost(postDTO, id);
		return new ResponseEntity<> (postResponse, HttpStatus.OK);
	
	}
	@ApiOperation(value = "Delete Post By Id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	//delete post rest api
	@DeleteMapping("api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}


