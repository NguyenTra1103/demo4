package com.example.demo4.repository;

import com.example.demo4.domain.SubComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubCommentRepository extends MongoRepository<SubComment, String> {
 List<SubComment> findSubCommentByCommentId(String commentId);


}
