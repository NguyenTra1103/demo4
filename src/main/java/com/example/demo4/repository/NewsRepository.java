
package com.example.demo4.repository;

import com.example.demo4.domain.News;
import com.example.demo4.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface NewsRepository extends MongoRepository<News, String>, PagingAndSortingRepository<News,String> {
    Optional<News> findById(String id);



}
