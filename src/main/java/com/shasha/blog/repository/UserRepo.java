package com.shasha.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shasha.blog.domain.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {
	
	Optional<UserEntity> findByEmail(String email);

}
