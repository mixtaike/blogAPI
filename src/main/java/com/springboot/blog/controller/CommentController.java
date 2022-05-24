package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.CommentDTO;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable long postId, @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
}
