package com.blogApp.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.application.entities.Comment;
import com.blogApp.application.payloads.ApiResponce;
import com.blogApp.application.payloads.CommentDTO;
import com.blogApp.application.services.CommentService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDto,
            @PathVariable Integer postId) {
        CommentDTO createdComment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<ApiResponce> deleteComment(
            @PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponce>(new ApiResponce("Comment deleted successfully", true), HttpStatus.OK);
    }

}
