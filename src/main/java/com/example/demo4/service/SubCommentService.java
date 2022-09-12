package com.example.demo4.service;

import com.example.demo4.domain.SubComment;
import com.example.demo4.request.subcomment.CreateSubCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubCommentService {
    SubComment findById(String id);
    void deleteSubComment(String id);
    SubComment insertSubComment(String token, CreateSubCommentRequest subCommentRequest);
    Page<SubComment> findAllSubComment(Pageable pageable);
    List<SubComment> findSubCommentByCommentId(String id);

}
