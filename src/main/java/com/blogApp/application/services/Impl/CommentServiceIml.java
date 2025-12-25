package com.blogApp.application.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.application.entities.Comment;
import com.blogApp.application.entities.Post;
import com.blogApp.application.payloads.CommentDTO;
import com.blogApp.application.repositories.CommentRepo;
import com.blogApp.application.repositories.PostRepo;
import com.blogApp.application.services.CommentService;

@Service
public class CommentServiceIml implements CommentService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new RuntimeException("Post not found with id: "+ postId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
      
    }

    @Override
    public void deleteComment(Integer commentId) {
        
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found with id: "+ commentId));
        this.commentRepo.delete(comment);
    }

}
