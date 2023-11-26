package com.shasha.blog.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shasha.blog.config.AppConstants;
import com.shasha.blog.payload.ApiResponse;
import com.shasha.blog.payload.UserDTO;
import com.shasha.blog.service.IUserService;

@RestController
@Valid
@RequestMapping("/userapi")
public class UserRestController {

	@Autowired
	private IUserService userServoice;

	@PostMapping("/save/{roleId}")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDto, @PathVariable Integer roleId) {
		UserDTO user = userServoice.addUser(userDto, roleId);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDto, @PathVariable Integer userId) {
		UserDTO updateUser = userServoice.updateUser(userDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(updateUser);
	}

	@GetMapping("getUser/{userId}")
	public ResponseEntity<?> getUserById(@Valid @PathVariable Integer userId) {
		UserDTO user = userServoice.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		List<UserDTO> users = userServoice.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		userServoice.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(AppConstants.DEL_SUC, true));
	}

}
