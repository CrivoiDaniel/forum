package com.example.forum.controller;


import com.example.forum.dto.CommentRequest;
import com.example.forum.dto.CommentResponse;
import com.example.forum.model.User;
import com.example.forum.service.CommentService;
import com.example.forum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @Valid @RequestBody
    CommentRequest request, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(commentService.createComment(request, postId, user));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByPost(@PathVariable Long postId) throws ExecutionException, InterruptedException {

        return ResponseEntity.ok(commentService.getAllCommentsByPost(postId));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(commentService.updateComment(request, id, user));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        commentService.deleteComment(id, user);

        return ResponseEntity.noContent().build();
    }
}
