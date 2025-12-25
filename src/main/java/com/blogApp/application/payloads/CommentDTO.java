package com.blogApp.application.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private Integer commentId;
    private String content;
}