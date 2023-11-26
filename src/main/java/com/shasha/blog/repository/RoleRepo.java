package com.shasha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shasha.blog.domain.RoleEntity;

public interface RoleRepo  extends JpaRepository<RoleEntity, Integer>{

}
