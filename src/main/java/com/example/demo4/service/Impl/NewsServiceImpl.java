package com.example.demo4.service.Impl;

import com.example.demo4.contant.Role;
import com.example.demo4.domain.Comment;
import com.example.demo4.domain.News;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.CommentRepository;
import com.example.demo4.repository.NewsRepository;
import com.example.demo4.repository.SubCommentRepository;
import com.example.demo4.repository.customize.NewsRepositoryCustomize;
import com.example.demo4.request.news.CreateNewsRequest;
import com.example.demo4.request.news.UpdateNewsRequest;
import com.example.demo4.response.NewsResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.NewsService;
import com.example.demo4.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsRepositoryCustomize newsRepositoryCustomize;
    private final JwtService jwtService;
    private final SubCommentRepository subCommentRepository;
    private final CommentRepository commentRepository;

    @Override
    public News insertNews(String token, CreateNewsRequest createNewsRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (role.equals(Role.ADMIN)) {
            String uid = jwtService.parseTokenToId(token);
            News news = new News();
            news.setUserId(uid);
            news.setTitle(createNewsRequest.getTitle());
            news.setContent(createNewsRequest.getContent());
            news.setLastUpdateTime(DateTimeUtil.getCurrentTime());
            news.setLastCreateTime(DateTimeUtil.getCurrentTime());
            news.setHashTag(createNewsRequest.getHashTag());
            news.setBanner(createNewsRequest.getBanner());
            return newsRepository.insert(news);
        }
        throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED,ObjectResponse.MESSAGE_UNAUTHORIZED);
    }

    @Override
    public News updateNews(String token, UpdateNewsRequest updateNewsRequest) {
        String uuid = jwtService.parseTokenToId(token);
        Optional<News> news = newsRepository.findById(updateNewsRequest.getId());
        news.get().setUpdateId(uuid);
        news.get().setTitle(updateNewsRequest.getTitle());
        news.get().setContent(updateNewsRequest.getContent());
        news.get().setHashTag(updateNewsRequest.getHashTag());
        news.get().setBanner(updateNewsRequest.getBanner());
        news.get().setLastUpdateTime(DateTimeUtil.getCurrentTime());
        return newsRepository.save(news.get());
    }

    @Override
    public Optional<News> findById(String id) {

        return newsRepository.findById(id);
    }


    @Override
    public void deleteNewsById(String id) {
        Optional<News> deleteNews = findById(id);
        if (deleteNews.isPresent()) {
            List<Comment> comments = deleteNews.get().getComments();
            commentRepository.deleteAll(comments);
            subCommentRepository.deleteAll();
            newsRepository.deleteById(id);
        }
    }
    public boolean isNewsExist(String id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent())
            return true;
        return false;
    }

    @Override
    public List<NewsResponse> findAll(String hashtag, int skip, int limit) {
        List<News> all = newsRepositoryCustomize.findAll(hashtag, skip, limit);
        List<NewsResponse> collect = all.stream()
                .map(news -> {
                    return NewsResponse.builder()
                            .id(news.getId())
                            .title(news.getTitle())
                            .banner(news.getBanner())
                            .hashtag(news.getHashTag())
                            .content(news.getContent())
                            .build();

                })
                .collect(Collectors.toList());
        return collect;
    }


}



