package com.example.demo4.service.Impl;

import com.example.demo4.domain.Comment;
import com.example.demo4.domain.News;
import com.example.demo4.domain.SubComment;
import com.example.demo4.repository.CommentRepository;
import com.example.demo4.repository.NewsRepository;
import com.example.demo4.repository.SubCommentRepository;
import com.example.demo4.request.comment.CreateCommentRequest;
import com.example.demo4.service.CommentService;
import com.example.demo4.service.JwtService;
import com.example.demo4.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  NewsRepository newsRepository;
    @Autowired
    private  SubCommentRepository subCommentRepository;

    @Override
    public Comment insertComment(String token, CreateCommentRequest createCommentRequest) {
        String uid = jwtService.parseTokenToId(token);
        Comment comment = new Comment();
        comment.setUserId(uid);
        comment.setNewsId(createCommentRequest.getNewsId());
        comment.setContent(createCommentRequest.getContent());
        comment.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
        commentRepository.insert(comment);
        return comment;
    }
    @Override
    public void deleteComment(String id) {
        Optional<Comment> commentUserId = findById(id);
        if (commentUserId.isPresent()) {
            List<SubComment> subComments = commentUserId.get().getSubComments();
            subCommentRepository.deleteAll(subComments);
            commentRepository.deleteById(id);
        }
    }
    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public Page<Comment> findAllComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }


}
