package com.shasha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shasha.blog.domain.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
