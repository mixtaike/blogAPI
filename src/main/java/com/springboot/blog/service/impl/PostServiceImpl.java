package com.springboot.blog.service.impl;




import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.dtos.PostDTO;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;
	
	
	private ModelMapper mapper;
	
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.mapper = mapper;
		this.postRepository = postRepository;
	}


	public PostDTO createPost(PostDTO postDto) {
		
		//convert dto to entity
		Post post = mapToEntity(postDto);
		
		
		Post newPost = postRepository.save(post);
		
		//convert entity to dto
		
		PostDTO postResponse = mapToDTO(newPost);
		
		return postResponse;
	}


	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort );
		
		
		
		Page<Post> posts = postRepository.findAll(pageable);
		
	

		List<Post> listOfPosts = posts.getContent();
		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	
	@Override
	public PostDTO getPostById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}
	
	
	
	
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		//get post by id from database 
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		
		Post updatedPost = postRepository.save(post);
		
		return mapToDTO(updatedPost);
	}
	
	
	
	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		
		postRepository.delete(post);
		
	}
	
	
	
	
	
	
	// convert entity into dto
	private PostDTO mapToDTO(Post post) {
		PostDTO postDTO = mapper.map(post, PostDTO.class);
		return postDTO;
	}
	
	// convert dto to entity
	
	private Post mapToEntity(PostDTO postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		return post;
						
	}


	


	


	
}
