package com.blogApp.application.payloads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"pageNumber","pageSize","totalElements","totalPages","lastPage","posts"})
public class PostResponce {

    List<PostDTO> posts;
    private Integer pageNumber; 
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;

}
