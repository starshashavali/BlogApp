package com.shasha.blog.payload;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	
	private Integer categoryId;

	@NotBlank(message="title should not be empty")
	private String title;

	@NotBlank(message="description should not be null")
	private String description;

}
