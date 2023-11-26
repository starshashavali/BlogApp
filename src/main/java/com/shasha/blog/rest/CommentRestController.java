package com.shasha.blog.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shasha.blog.config.AppConstants;
import com.shasha.blog.payload.ApiResponse;
import com.shasha.blog.payload.CommentDTO;
import com.shasha.blog.service.ICommenetService;

@RestController
@RequestMapping("/comment/api")
public class CommentRestController {
	
	@Autowired
	private ICommenetService commentService;
	
	@PostMapping("/save/{postId}")
	public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDto,
			@PathVariable Integer postId){
		CommentDTO comment = commentService.addComment(commentDto,postId);
		return ResponseEntity.status(HttpStatus.OK).body(comment);
	}
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<?> getCommentById(@PathVariable Integer commentId){
		CommentDTO commentDTO = commentService.getCommentById(commentId);
		return ResponseEntity.status(HttpStatus.OK).body(commentDTO);		
	}
	
	@DeleteMapping("delete/{commentId}")
	public ResponseEntity<?> deleteCommentById(@PathVariable Integer commentId){
		commentService.deleteComment(commentId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(AppConstants.DEL_SUC,true));
	}
	
	
	
	

}
