package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dtos.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment (long postId, CommentDTO commentDTO);
	
	List<CommentDTO> getCommentsByPostId(long postId);
	
	CommentDTO getCommentById(Long postId, Long commentId);
	
	CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest);

}
