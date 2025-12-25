package com.blogApp.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.application.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}