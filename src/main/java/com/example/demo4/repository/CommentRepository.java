package com.example.demo4.repository;

import com.example.demo4.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

   Optional<Comment> findById(String id);
}
