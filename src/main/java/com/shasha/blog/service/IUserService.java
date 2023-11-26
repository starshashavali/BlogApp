package com.shasha.blog.service;

import java.util.List;

import com.shasha.blog.payload.UserDTO;

public interface IUserService {
	UserDTO addUser(UserDTO userDto,Integer roleId);
	UserDTO updateUser(UserDTO userDto,Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
}
