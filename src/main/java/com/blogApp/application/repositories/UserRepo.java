package com.blogApp.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.application.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}