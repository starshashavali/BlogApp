package com.shasha.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shasha.blog.domain.Category;
import com.shasha.blog.domain.PostEntity;
import com.shasha.blog.domain.UserEntity;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {
	
	List<PostEntity> findByUserEntity(UserEntity userEntity);
	
	List<PostEntity> findByCategory(Category category);
	
	List<PostEntity> findByTitleContaining(String title);

}
