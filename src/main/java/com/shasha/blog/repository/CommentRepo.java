package com.shasha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shasha.blog.domain.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
