package com.blogApp.application.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogApp.application.entities.Category;
import com.blogApp.application.entities.Post;
import com.blogApp.application.entities.User;
import com.blogApp.application.exceptions.ResourceNotFoundException;
import com.blogApp.application.payloads.PostDTO;
import com.blogApp.application.payloads.PostResponce;
import com.blogApp.application.repositories.CategoryRepo;
import com.blogApp.application.repositories.PostRepo;
import com.blogApp.application.repositories.UserRepo;
import com.blogApp.application.services.PostService;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImg("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        PostDTO postDTO = this.modelMapper.map(newPost, PostDTO.class);

        return postDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImg(postDto.getImg());

        Post updatedPost = this.postRepo.save(post);
        PostDTO postDTO = this.modelMapper.map(updatedPost, PostDTO.class);

        return postDTO;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));
        PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    @Override
    public PostResponce getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if(sortDir.equals("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> page = this.postRepo.findAll(pageable);
        List<Post> allPosts = page.getContent();
        List<PostDTO> allPostsDTO = allPosts.stream()
                .map(post -> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponce postResponce = new PostResponce();
        postResponce.setPosts(allPostsDTO);
        postResponce.setPageNumber(page.getNumber());
        postResponce.setPageSize(page.getSize());
        postResponce.setTotalElements(page.getTotalElements());
        postResponce.setTotalPages(page.getTotalPages());
        postResponce.setLastPage(page.isLast());

        return postResponce;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User_Id", userId));
        List<Post> postsByUser = this.postRepo.findByUser(user);
        return postsByUser.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", categoryId));
        List<Post> postsByCategory = this.postRepo.findByCategory(category);
        return postsByCategory.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }
}
