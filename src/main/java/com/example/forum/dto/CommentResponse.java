package com.example.forum.dto;

import com.example.forum.model.Post;
import com.example.forum.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String message;
    private Post post;
    private User user;
    private LocalDate create_at;
}
