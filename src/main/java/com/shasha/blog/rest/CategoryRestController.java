package com.shasha.blog.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.shasha.blog.payload.CategoryDTO;
import com.shasha.blog.service.ICategoryService;

@RestController
@Valid
@RequestMapping("/category/api")
public class CategoryRestController {

	@Autowired
	private ICategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO dto = categoryService.addCategory(categoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO categoryDTO2 = categoryService.updateCategoryDTO(categoryDto);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDTO2);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategory(@Valid @PathVariable Integer categoryId) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
	}
	@GetMapping("/getAllCategories")
	public ResponseEntity<?>getAllCategories(){
		List<CategoryDTO> list = categoryService.getAllCategory();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@Valid @PathVariable Integer categoryId){
		categoryService.deleteCotegory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(AppConstants.DEL_SUC,true));
	}

}
