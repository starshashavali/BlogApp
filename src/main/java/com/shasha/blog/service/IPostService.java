package com.shasha.blog.service;

import java.util.List;

import com.shasha.blog.payload.PostDTO;
import com.shasha.blog.payload.PostResponse;

public interface IPostService {

	PostDTO savePost(PostDTO postDto,Integer userId,Integer categoryId);

	PostDTO updatePost(Integer postId, PostDTO postDto);

	PostDTO getPostById(Integer postId);

	PostResponse getAllPosts(Integer pageNo ,Integer pageSize,String sortBy,String sortDir);

	List<PostDTO> getPostsByUser(Integer userId);

	List<PostDTO> getPostsByCategory(Integer categoryId);
	
	List<PostDTO>searchPosts(String keyword);

	void deleteById(Integer postId);

}
