package com.blogApp.application.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogApp.application.entities.Comment;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"postId","title","content","img","addedDate","user","category"})
public class PostDTO {

    private Integer postId;
    private String title;
    private String content;
    private String img;
    private Date addedDate;

    private UserDTO user;
    private CategoryDTO category;

    private Set<CommentDTO> comments = new HashSet();

}
