package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtos.CommentDTO;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	private PostRepository postRepository;	
	
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {

		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}


	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) {
		
		Comment comment = mapToEntity(commentDTO);
		
		
		// retrieve post entity by id 
		
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		//save comment entity to DB
		
		Comment newComment = commentRepository.save(comment);
		
		return mapToDTO(newComment);
	}
	
	
	private CommentDTO mapToDTO(Comment comment) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(comment.getId());
		commentDTO.setBody(comment.getBody());
		commentDTO.setEmail(comment.getEmail());
		commentDTO.setName(comment.getName());
		return commentDTO;
	}
	
	private Comment mapToEntity(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setId(commentDTO.getId());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());
		comment.setName(commentDTO.getName());
		
		return comment;
	}



	public List<CommentDTO> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findPostById(postId);
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
		
		
	}


	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		return mapToDTO(comment);
	}
	

}
