package com.shasha.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private List<PostDTO> content;

	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalRecords;
	private Integer totalPages;
	private boolean lastPage;

}
