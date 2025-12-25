package com.blogApp.application.services;

import java.util.List;

import com.blogApp.application.payloads.CategoryDTO;

public interface CategoryService {

	CategoryDTO createCategory(CategoryDTO category);
	CategoryDTO UpdateCategory(CategoryDTO category, Integer categoryId);
	CategoryDTO getCategoryById(Integer categoryId);
	List<CategoryDTO> getAllCategories();
	void deleteCategory(Integer categoryId);
}
