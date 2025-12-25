package com.blogApp.application.services;

import com.blogApp.application.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId);
    void deleteComment(Integer commentId);
}
