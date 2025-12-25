package com.blogApp.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.application.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
