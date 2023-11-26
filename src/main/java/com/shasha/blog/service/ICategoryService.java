package com.shasha.blog.service;

import java.util.List;

import com.shasha.blog.payload.CategoryDTO;

public interface ICategoryService {
	
	CategoryDTO addCategory(CategoryDTO category);
	
	CategoryDTO updateCategoryDTO(CategoryDTO categoryDto);
	
	CategoryDTO getCategoryById(Integer categoryId);
	
	List<CategoryDTO> getAllCategory();
	
	void deleteCotegory(Integer categoryId);

}
