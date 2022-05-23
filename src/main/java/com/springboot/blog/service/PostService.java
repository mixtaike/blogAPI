package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dtos.PostDTO;
import com.springboot.blog.dtos.PostResponse;

public interface PostService {
	PostDTO createPost(PostDTO postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize);
	
	PostDTO getPostById(long id);
	
	PostDTO updatePost(PostDTO postDTO, long id);
	
	void deletePostById(long id);
}

