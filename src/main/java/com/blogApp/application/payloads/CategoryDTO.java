package com.blogApp.application.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private int categoryId;

    @NotEmpty(message = "Category title must not be empty")
    @Size(min = 3, max = 100, message = "Category title must be between 3 and 100 characters")
    private String categoryTitle;

    @NotEmpty(message = "Category description must not be empty")
    @Size(min = 10, max = 500, message = "Category description must be between 10 and 500 characters")
    private String categoryDescription;

}
