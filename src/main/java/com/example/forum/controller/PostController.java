package com.example.forum.controller;

import com.example.forum.dto.PostRequest;
import com.example.forum.dto.PostResponse;
import com.example.forum.model.Post;
import com.example.forum.model.User;
import com.example.forum.service.PostService;
import com.example.forum.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid  @RequestBody PostRequest request, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(postService.createPost(request,user));
    }

    @GetMapping
    public List<Post> getAllPosts() throws  ExecutionException, InterruptedException {
        return postService.getAllPosts();
    }

    @GetMapping("/my-posts")
    public List<Post> getPostByUser(Authentication authentication) throws  ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return  postService.getPostByUser(user);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return postService.updatePost(post, id, user);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) throws ExecutionException, InterruptedException {
        postService.deletePost(id);
    }
}
