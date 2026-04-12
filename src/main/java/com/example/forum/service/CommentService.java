package com.example.forum.service;

import com.example.forum.dto.CommentRequest;
import com.example.forum.dto.CommentResponse;
import com.example.forum.model.Comment;
import com.example.forum.model.Post;
import com.example.forum.model.User;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @CacheEvict(value = "posts", allEntries = true)
    public CommentResponse createComment(CommentRequest request, Long postId, User user) throws ExecutionException, InterruptedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setMessage(request.getMessage());
        comment.setPost(post);
        comment.setUser(user);
        Comment saved = commentRepository.save(comment);

        return new CommentResponse(saved.getId(), saved.getMessage(), saved.getPost(), saved.getUser(), saved.getCreate_at());
    }
    @Cacheable("comments")
    public List<CommentResponse> getAllCommentsByPost(Long postId) throws ExecutionException, InterruptedException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return  commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getMessage(), comment.getPost(), comment.getUser(), comment.getCreate_at()))
                .toList();
    }
    @CacheEvict(value = "posts", allEntries = true)
    public CommentResponse updateComment(CommentRequest request, Long id, User user) throws ExecutionException, InterruptedException {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!existingComment.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You can only edit your own comments");
        }
        existingComment.setMessage(request.getMessage());
        Comment saved = commentRepository.save(existingComment);

        return new CommentResponse(saved.getId(), saved.getMessage(), saved.getPost(), saved.getUser(), saved.getCreate_at());
    }

    @CacheEvict(value = "posts", allEntries = true)
    public void deleteComment(Long id, User user) throws ExecutionException, InterruptedException {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!existingComment.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You can only remove your own comments");
        }
        commentRepository.deleteById(id);
    }
}
