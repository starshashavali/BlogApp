package com.shasha.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shasha.blog.domain.Comment;
import com.shasha.blog.domain.PostEntity;
import com.shasha.blog.exception.IdNotFoundException;
import com.shasha.blog.payload.CommentDTO;
import com.shasha.blog.repository.CommentRepo;
import com.shasha.blog.repository.PostRepo;
import com.shasha.blog.service.ICommenetService;

@Service
public class CommentServiceImpl implements ICommenetService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDTO addComment(CommentDTO commentDto,Integer postId) {
	
		Comment entity = mapper.map(commentDto, Comment.class);
		PostEntity post = postRepo.findById(postId).orElseThrow(()->
		new IdNotFoundException("CommentId not found"+postId));
		entity.setPost(post);
		Comment commentEntity = commentRepo.save(entity);
		
		return mapper.map(commentEntity, CommentDTO.class);
	}

	@Override
	public CommentDTO getCommentById(Integer commentId) {
		 Comment entity = commentRepo.findById(commentId).orElseThrow(()->
		 new IdNotFoundException("CommentId not found"+commentId));
		 
		return mapper.map(entity, CommentDTO.class);
	}

	@Override
	public List<CommentDTO> getAllComments() {
		
		return null;
	}

	@Override
	public void deleteComment(Integer commentId) {
		 Comment entity = commentRepo.findById(commentId).orElseThrow(()->
		 new IdNotFoundException("CommentId not found"+commentId));
		 
		 commentRepo.delete(entity);;
	}

}
