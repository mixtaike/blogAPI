package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	private ModelMapper mapper;
	
	
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {

		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}


	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) {
		
		Comment comment = mapToEntity(commentDTO);
		
		
		// retrieve post entity by id 
		
		Post post = findPostById(postId);
		
		//set post to comment entity
		comment.setPost(post);
		
		//save comment entity to DB
		
		Comment newComment = commentRepository.save(comment);
		
		return mapToDTO(newComment);
	}
	



	public List<CommentDTO> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
		
		
	}


	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) {
		
		Post post = findPostById(postId);
		Comment comment = findCommentById(commentId);
		
		validarId(comment, post);
		
		return mapToDTO(comment);
	}


	@Override
	public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {
		

		Post post = findPostById(postId);
		Comment comment = findCommentById(commentId);
		
		
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		
		return mapToDTO(updatedComment);
	}


	@Override
	public void deleteComment(Long postId, Long commentId) {
		

		Post post = findPostById(postId);
		Comment comment = findCommentById(commentId);
		
		validarId(comment, post);
		
		commentRepository.delete(comment);
		
	}
	
	
	
	public BlogAPIException validarId(Comment comment, Post post) {
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
			
		}
		
		else {
			return null;
		}
	}
	
	
	
	
	private CommentDTO mapToDTO(Comment comment) {
		CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
		return commentDTO;
	}
	
	// convert dto to entity
	
	private Comment mapToEntity(CommentDTO commentDTO) {
		
		Comment comment = mapper.map(commentDTO, Comment.class);
		return comment;
						
	}



	
	public Comment findCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		 return comment;
	}
	
	public Post findPostById(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		return post;
	}

}
