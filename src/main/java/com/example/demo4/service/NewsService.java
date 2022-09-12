package com.example.demo4.service;

import com.example.demo4.domain.News;
import com.example.demo4.request.news.CreateNewsRequest;
import com.example.demo4.request.news.UpdateNewsRequest;
import com.example.demo4.response.NewsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    News insertNews(String token, CreateNewsRequest createNewsRequest);
    News updateNews( String token, UpdateNewsRequest updateNewsRequest);
    Optional<News> findById(String id);
    void deleteNewsById(String id);
    boolean isNewsExist(String id);

    List<NewsResponse> findAll(String hashtag , int skip , int limit);
}
