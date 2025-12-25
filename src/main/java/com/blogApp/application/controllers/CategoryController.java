package com.blogApp.application.controllers;

import java.util.List;

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

import com.blogApp.application.payloads.ApiResponce;
import com.blogApp.application.payloads.CategoryDTO;
import com.blogApp.application.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("newCategory")
	public ResponseEntity<CategoryDTO> createUser(@RequestBody @Valid CategoryDTO categoryDto) {
		CategoryDTO createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}

	@GetMapping("/getCategory/{categoryId}")
	public ResponseEntity<CategoryDTO> getUser(@PathVariable Integer categoryId) {
		CategoryDTO categoryDto = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}

	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categoryDtos = this.categoryService.getAllCategories();
		return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
	}

	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<CategoryDTO> updateUser(@RequestBody @Valid CategoryDTO categoryDto, @PathVariable Integer categoryId) {
		CategoryDTO updatecategoryDto = this.categoryService.UpdateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updatecategoryDto, HttpStatus.OK);
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ApiResponce> deleteUser(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity(new ApiResponce("Category deleted successfully", true), HttpStatus.OK);
	}


}