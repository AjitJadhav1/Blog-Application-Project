package com.blogApp.application.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.application.entities.Category;
import com.blogApp.application.exceptions.ResourceNotFoundException;
import com.blogApp.application.payloads.CategoryDTO;
import com.blogApp.application.repositories.CategoryRepo;
import com.blogApp.application.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category category = this.categoryRepo.save(this.dtoToCategory(categoryDto));
        return this.categoryToDto(category);
    }

    @Override
    public CategoryDTO UpdateCategory(CategoryDTO categoryDto, Integer category_Id) {
        Category category = this.categoryRepo.findById(category_Id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", category_Id));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        return this.categoryToDto(this.categoryRepo.save(category));
    }

    @Override
    public CategoryDTO getCategoryById(Integer category_Id) {
        Category category = this.categoryRepo.findById(category_Id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", category_Id));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream()
                .map(category -> this.categoryToDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer category_Id) {
        Category category = this.categoryRepo.findById(category_Id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", category_Id));
        this.categoryRepo.delete(category);
    }

    private Category dtoToCategory(CategoryDTO categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDTO categoryToDto(Category category) {
        return this.modelMapper.map(category, CategoryDTO.class);
    }
}