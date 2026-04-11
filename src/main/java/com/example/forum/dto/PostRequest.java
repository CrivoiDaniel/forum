package com.example.forum.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    @NotBlank(message = "Title are required")
    private String title;

    @NotBlank(message = "Content are required")
    private String content;
}
