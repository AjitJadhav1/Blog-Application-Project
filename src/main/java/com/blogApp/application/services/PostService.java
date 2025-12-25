package com.blogApp.application.services;

import java.util.List;

import com.blogApp.application.payloads.PostDTO;
import com.blogApp.application.payloads.PostResponce;

public interface PostService {

    PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDto, Integer postId);
    PostDTO getPostById(Integer postId);
    PostResponce getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deletePost(Integer postId);
    List<PostDTO> getPostsByCategory(Integer categoryId);
    List<PostDTO> getPostsByUser(Integer userId);
    List<PostDTO> searchPosts(String keyword);
}
