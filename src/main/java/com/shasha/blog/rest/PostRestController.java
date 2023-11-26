package com.shasha.blog.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shasha.blog.config.AppConstants;
import com.shasha.blog.payload.ApiResponse;
import com.shasha.blog.payload.PostDTO;
import com.shasha.blog.payload.PostResponse;
import com.shasha.blog.service.IFileService;
import com.shasha.blog.service.IPostService;

@RestController
@RequestMapping("/post/api")
public class PostRestController {
	@Autowired
	private IPostService postService;

	@Autowired
	private IFileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<?> savePost(@RequestBody PostDTO postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO post = postService.savePost(postDto, userId, categoryId);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}

	@PutMapping("/update/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updatePost(@PathVariable Integer postId, @RequestBody PostDTO postDto) {
		PostDTO updatePost = postService.updatePost(postId, postDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatePost);

	}

	@GetMapping("/post/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getPostById(@PathVariable Integer postId) {
		PostDTO postDto = postService.getPostById(postId);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);
	}

	@PreAuthorize("haeRole('USER')")
	@GetMapping("/getAllPosts")
	public ResponseEntity<?> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORTBY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.ASC, required = false) String sortDir) {

		PostResponse response = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("User/{userId}/posts")
	public ResponseEntity<?> getPostsByUser(@PathVariable Integer userId) {
		List<PostDTO> postsByUser = postService.getPostsByUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(postsByUser);
	}

	@GetMapping("Category/{categoryId}/posts")
	public ResponseEntity<?> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDTO> postsByCategory = postService.getPostsByCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(postsByCategory);
	}

	@GetMapping("posts/search/{title}")
	public ResponseEntity<?> searchPostsByTitle(@PathVariable("title") String title) {
		List<PostDTO> posts = postService.searchPosts(title);
		System.out.println(posts);
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@DeleteMapping("/delete/(postId}")
	public ResponseEntity<?> deletPostById(@PathVariable Integer postId) {
		postService.deleteById(postId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(AppConstants.DEL_SUC, true));

	}

	@PostMapping("post/image/upload/{postId}")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDTO postDTO = postService.getPostById(postId);

		String imageName = fileService.uploadImage(path, image);
		postDTO.setImageName(imageName);
		PostDTO updatePost = postService.updatePost(postId, postDTO);
		return ResponseEntity.status(HttpStatus.OK).body(updatePost);
	}

	@GetMapping(value = "/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}
}
