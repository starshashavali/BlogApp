package com.shasha.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
	
	private Integer postId;

	private String title;

	private String content;

	private String imageName;

	private CategoryDTO category;

	private UserDTO userEntity;
	
	private List<CommentDTO> comment;

}
