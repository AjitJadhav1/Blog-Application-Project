package com.blogApp.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.application.entities.Category;
import com.blogApp.application.entities.Post;
import com.blogApp.application.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);

    List<Post> findByTitleContaining(String title);

}
