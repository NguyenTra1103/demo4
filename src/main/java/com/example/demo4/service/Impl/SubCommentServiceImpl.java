package com.example.demo4.service.Impl;
import com.example.demo4.domain.Comment;
import com.example.demo4.domain.News;
import com.example.demo4.domain.SubComment;
import com.example.demo4.repository.CommentRepository;
import com.example.demo4.repository.NewsRepository;
import com.example.demo4.repository.SubCommentRepository;
import com.example.demo4.request.subcomment.CreateSubCommentRequest;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.SubCommentService;
import com.example.demo4.utils.DateTimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCommentServiceImpl implements SubCommentService {
    private final JwtService jwtService;
    private final SubCommentRepository subCommentRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    public SubCommentServiceImpl(JwtService jwtService, SubCommentRepository subCommentRepository, CommentRepository commentRepository, NewsRepository newsRepository) {
        this.jwtService = jwtService;
        this.subCommentRepository = subCommentRepository;
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public SubComment insertSubComment(String token, CreateSubCommentRequest subCommentRequest) {
        String uid = jwtService.parseTokenToId(token);
        String commentId = subCommentRequest.getCommentId();
        String newsId = subCommentRequest.getNewsId();
        Comment comment = commentRepository.findById(commentId).get();
        News news = newsRepository.findById(newsId).get();
        SubComment subComment = new SubComment();
        subComment.setUserId(uid);
        subComment.setCommentId(subCommentRequest.getCommentId());
        subComment.setContent(subCommentRequest.getContent());
        subComment.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
        subComment.setNewsId(subCommentRequest.getNewsId());
        subCommentRepository.insert(subComment);
        comment.getSubComments().add(subComment);
        news.getComments().add(comment);
        comment.setCountSubComment(comment.getSubComments().size());
        commentRepository.save(comment);
        newsRepository.save(news);
        return subComment;
    }
    @Override
    public Page<SubComment> findAllSubComment(Pageable pageable) {
        return subCommentRepository.findAll(pageable);
    }

    @Override
    public List<SubComment> findSubCommentByCommentId(String id) {
        return subCommentRepository.findSubCommentByCommentId(id);
    }

    @Override
    public SubComment findById(String id) {
        return subCommentRepository.findById(id).get();
    }

    @Override
    public void deleteSubComment(String id) {
        SubComment subComment = findById(id);
        if (subComment != null){
            subCommentRepository.delete(subComment);}
    }
}