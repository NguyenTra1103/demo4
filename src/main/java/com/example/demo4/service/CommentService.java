package com.example.demo4.service;

import com.example.demo4.domain.Comment;
import com.example.demo4.request.comment.CreateCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface CommentService {
    Comment insertComment(String token, CreateCommentRequest createCommentRequest);
    void deleteComment(String id);
    Optional<Comment> findById(String id);
    Page<Comment> findAllComment(Pageable pageable);
}
