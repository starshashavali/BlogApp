package com.shasha.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shasha.blog.domain.Category;
import com.shasha.blog.exception.IdNotFoundException;
import com.shasha.blog.payload.CategoryDTO;
import com.shasha.blog.repository.CategoryRepo;
import com.shasha.blog.service.ICategoryService;

@Service
public class ICategoryServiceImpl implements ICategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDTO addCategory(CategoryDTO category) {
	 Category entity = mapper.map(category, Category.class);
	 Category categoryEntity = categoryRepo.save(entity);
	 return  mapper.map(categoryEntity, CategoryDTO.class);	 
	}

	@Override
	public CategoryDTO updateCategoryDTO(CategoryDTO categoryDto) {
		 Category entity = mapper.map(categoryDto, Category.class);
		 Category categoryEntity = categoryRepo.save(entity);
		return mapper.map(categoryEntity, CategoryDTO.class);	
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
	Category entity = categoryRepo.findById(categoryId)
				.orElseThrow(()->
				new IdNotFoundException("Category Id not found"+categoryId));
		return mapper.map(entity, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		return categoryRepo.findAll()
		.stream()
		.map(entity->mapper.map(entity, CategoryDTO.class))
		.collect(Collectors.toList());
	}

	@Override
	public void deleteCotegory(Integer categoryId) {
		categoryRepo.findById(categoryId)
		.orElseThrow(()->new IdNotFoundException("Category Id not found"+categoryId));
		categoryRepo.deleteById(categoryId);

	}

}
