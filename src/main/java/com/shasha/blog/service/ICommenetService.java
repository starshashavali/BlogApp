package com.shasha.blog.service;

import java.util.List;

import com.shasha.blog.payload.CommentDTO;

public interface ICommenetService {
	
	
	public CommentDTO addComment(CommentDTO commentDto,Integer postId);
	
	
	public CommentDTO getCommentById(Integer commentId);
	
	
	public List<CommentDTO> getAllComments();
	
	
	public void deleteComment(Integer commentId);

}
