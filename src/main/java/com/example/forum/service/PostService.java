package com.example.forum.service;

import com.example.forum.model.Post;
import com.example.forum.model.User;
import com.example.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @CacheEvict(value = "posts", allEntries = true)
    public Post createPost(Post post, User user) throws ExecutionException, InterruptedException {
        post.setUser(user);
        return postRepository.save(post);
    }
    @Cacheable("posts")
    public List<Post> getAllPosts() throws  ExecutionException, InterruptedException {
        return postRepository.findAll();
    }
    @Cacheable("posts")
    public List<Post> getPostByUser(User user) throws ExecutionException, InterruptedException {
        return postRepository.findByUserId(user.getId());
    }
    @CacheEvict(value = "posts", allEntries = true)
    public Post updatePost(Post updatedPost, Long id, User user) throws ExecutionException, InterruptedException {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        return postRepository.save(existingPost);
    }

    @CacheEvict(value = "posts", allEntries = true)
    public void deletePost(Long id) throws ExecutionException, InterruptedException {
        if(!postRepository.existsById(id)){
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
    }
}
