package com.springboot.blog.service;


import com.springboot.blog.dtos.PostDTO;
import com.springboot.blog.dtos.PostResponse;

public interface PostService {
	PostDTO createPost(PostDTO postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDTO getPostById(long id);
	
	PostDTO updatePost(PostDTO postDTO, long id);
	
	void deletePostById(long id);
}
