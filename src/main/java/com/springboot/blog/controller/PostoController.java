package com.springboot.blog.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.springboot.blog.dtos.PostDTOV2;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping()
public class PostoController {
	
	private PostService postService;

	public PostoController(PostService postService) {
		super();
		this.postService = postService;
	}

	
	// create blog post api
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}
	
	//get all posts rest api
	
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
	
	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=1")
	public ResponseEntity<PostDTO> getPostByIdV1(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=2")
	public ResponseEntity<PostDTOV2> getPostByIdV2(@PathVariable Long id) {
		PostDTO postDTO = postService.getPostById(id);
		PostDTOV2 postDtov2 = new PostDTOV2();
		postDtov2.setId(postDTO.getId());
		postDtov2.setTitle(postDTO.getTitle());
		postDtov2.setDescription(postDTO.getDescription());
		postDtov2.setContent(postDTO.getContent());
		List<String> tags = new ArrayList<>();
		tags.add("Java");
		tags.add("Spring boot");
		tags.add("AWS");
		postDtov2.setTags(tags);
		return ResponseEntity.ok(postDtov2);
	}
	
	//update post by id rest apoi
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("api/v1/posts/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable int id ) {
		PostDTO postResponse=  postService.updatePost(postDTO, id);
		return new ResponseEntity<> (postResponse, HttpStatus.OK);
	
	}
	@PreAuthorize("hasRole('ADMIN')")
	//delete post rest api
	@DeleteMapping("api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}


