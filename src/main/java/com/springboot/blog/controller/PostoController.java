package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("api/posts")
public class PostoController {
	
	private PostService postService;

	public PostoController(PostService postService) {
		super();
		this.postService = postService;
	}

	
	// create blog post api
	
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
		
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}
	
	//get all posts rest api
	
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue = "0", required = false)int pageNo, 
			@RequestParam(value = "pageSize", defaultValue ="10", required = false) int pageSize
			){
		return postService.getAllPosts(pageNo, pageSize);
	}
	
	//get post by id 
	
	@GetMapping("{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//update post by id rest apoi
	
	@PutMapping("{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable int id ) {
		PostDTO postResponse=  postService.updatePost(postDTO, id);
		return new ResponseEntity<> (postResponse, HttpStatus.OK);
	
	}
	
	//delete post rest api
	@DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}


