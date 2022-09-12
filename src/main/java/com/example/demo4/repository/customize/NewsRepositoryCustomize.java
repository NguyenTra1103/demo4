package com.example.demo4.repository.customize;

import com.example.demo4.domain.News;

import java.util.List;

public interface NewsRepositoryCustomize {
    List<News> findAll(String hashtag,int limit , int skip);
}
