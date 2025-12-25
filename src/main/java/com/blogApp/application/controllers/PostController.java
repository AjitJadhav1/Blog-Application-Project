package com.blogApp.application.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.application.configuration.AppConstants;
import com.blogApp.application.payloads.ApiResponce;
import com.blogApp.application.payloads.PostDTO;
import com.blogApp.application.payloads.PostResponce;
import com.blogApp.application.payloads.UserDTO;
import com.blogApp.application.services.FileService;
import com.blogApp.application.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String imagePath;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(
            @RequestBody PostDTO postDTO,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("getPost/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
    }

    @GetMapping("getAllPosts")
    public ResponseEntity<PostResponce> getAllPosts(
        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponce postResponce = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponce>(postResponce, HttpStatus.OK);
    }

    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        List<PostDTO> postDTO = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDTO>>(postDTO, HttpStatus.OK);
    }

    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postDTO = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(postDTO, HttpStatus.OK);
    }

    @PutMapping("updatePost/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("deletePost/{postId}")
    public ResponseEntity<ApiResponce> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponce>(new ApiResponce(AppConstants.POST_DELETED_SUCCESSFULLY, true), HttpStatus.OK);
    }

    @GetMapping("posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostsByTitle(@PathVariable String keyword) {
        List<PostDTO> result = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImg (
        @RequestParam("image") MultipartFile image,
        @PathVariable Integer postId
    ) throws IOException {
        PostDTO postDTO = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(this.imagePath, image);
        postDTO.setImg(fileName);
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(
            @PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(this.imagePath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
