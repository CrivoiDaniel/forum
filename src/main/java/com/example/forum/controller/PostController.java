package com.example.forum.controller;

import com.example.forum.model.Post;
import com.example.forum.model.User;
import com.example.forum.service.PostService;
import com.example.forum.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Post createPost(@Valid  @RequestBody Post post, Authentication authentication) throws ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return postService.createPost(post, user);
    }

    @GetMapping
    public List<Post> getAllPosts() throws  ExecutionException, InterruptedException {
        return postService.getAllPosts();
    }

    @GetMapping("/my-posts")
    public List<Post> getPostById(Authentication authentication) throws  ExecutionException, InterruptedException {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return  postService.getPostByUser(user);
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
