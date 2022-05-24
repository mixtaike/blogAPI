package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.blog.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findPostById(Long postId);

}
