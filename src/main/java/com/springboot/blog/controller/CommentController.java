package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.CommentDTO;
import com.springboot.blog.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD REST APIs for Comment Resource ")
@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@ApiOperation(value = "Create Comment REST API")
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable long postId, @Valid @RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Get All Comments By Post Id REST API")
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@ApiOperation(value = "Get single Comment By Id REST API")
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
		
		CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update Comment By Id REST API")
	@PutMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody CommentDTO commentDto){
		CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete Comment By Id REST API")
	@DeleteMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}
	
}
