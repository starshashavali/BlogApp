package com.shasha.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shasha.blog.domain.Category;
import com.shasha.blog.domain.PostEntity;
import com.shasha.blog.domain.UserEntity;
import com.shasha.blog.exception.IdNotFoundException;
import com.shasha.blog.payload.PostDTO;
import com.shasha.blog.payload.PostResponse;
import com.shasha.blog.repository.CategoryRepo;
import com.shasha.blog.repository.PostRepo;
import com.shasha.blog.repository.UserRepo;
import com.shasha.blog.service.IPostService;

@Service
public class IPostServiceImpl implements IPostService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IPostServiceImpl.class);

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PostDTO savePost(PostDTO postDto, Integer userId, Integer categoryId) {

		UserEntity userEntity = userRepo.findById(userId)
				.orElseThrow(() -> new IdNotFoundException("UserId not found" + userId));

		Category categoryEntity = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new IdNotFoundException("categoryId not found" + categoryId));

		PostEntity entity = mapper.map(postDto, PostEntity.class);

		entity.setImageName("default.png");
		entity.setAddedDate(new Date());
		entity.setCategory(categoryEntity);
		entity.setUserEntity(userEntity);
	

		PostEntity postEntity = postRepo.save(entity);
		
		
		return mapper.map(postEntity, PostDTO.class);

	}

	@Override
	public PostDTO updatePost(Integer postId, PostDTO postDto) {

		PostEntity postEntity = postRepo.findById(postId)
				.orElseThrow(() -> new IdNotFoundException("PostId not found" + postId));
		postEntity.setAddedDate(new Date());
		postEntity.setImageName(postDto.getImageName());
		postEntity.setContent(postDto.getContent());
		postEntity.setTitle(postDto.getTitle());
		PostEntity updatePost = postRepo.save(postEntity);

		return mapper.map(updatePost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		PostEntity postEntity = postRepo.findById(postId)
				.orElseThrow(() -> new IdNotFoundException("PostId not found" + postId));
		
		
		return mapper.map(postEntity, PostDTO.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		// ternary Operation
		Sort sort = ("ASC".equalsIgnoreCase(sortDir)) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

		Page<PostEntity> postEntity = postRepo.findAll(pageRequest);
		// List<PostEntity> posts = postEntity.getContent();

		List<PostDTO> postDtos = postEntity.stream().map(post -> mapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse response = new PostResponse();
		response.setContent(postDtos);
		response.setPageNumber(postEntity.getNumber());
		response.setTotalPages(postEntity.getTotalPages());
		response.setPageSize(postEntity.getSize());
		response.setTotalRecords(postEntity.getNumberOfElements());
		response.setLastPage(postEntity.isLast());
		return response;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		LOGGER.info("request to GetPostByUser endpoint::" + userId);

		UserEntity userEntity = userRepo.findById(userId)
				.orElseThrow(() -> new IdNotFoundException("UserId not found" + userId));

		return postRepo.findByUserEntity(userEntity).stream().map(posts -> mapper.map(posts, PostDTO.class))
				.collect(Collectors.toList());

	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		LOGGER.info("request to PostByCategory endpoint::" + categoryId);

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new IdNotFoundException("CategoryId not found" + categoryId));
		List<PostEntity> categoryPosts = postRepo.findByCategory(category);
		return categoryPosts.stream().map(posts -> mapper.map(posts, PostDTO.class)).collect(Collectors.toList());

	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {

		List<PostEntity> title = postRepo.findByTitleContaining(keyword);
		LOGGER.info("request to search endpoint::" + title);
		return title.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(Integer postId) {
		PostEntity postEntity = postRepo.findById(postId)
				.orElseThrow(() -> new IdNotFoundException("PostId not found" + postId));
		postRepo.delete(postEntity);

	}

}
