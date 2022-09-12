package com.example.demo4.repository.customize.Impl;

import com.example.demo4.domain.News;
import com.example.demo4.repository.customize.NewsRepositoryCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewRepositoryCustomizeImpl implements NewsRepositoryCustomize {

    private final MongoTemplate mongoTemplate;
    @Override
    public List<News> findAll(String hashtag, int limit, int skip) {
        Query query = new Query();
        if (hashtag != null) {
            query.addCriteria(Criteria.where("hashTag").is(hashtag));
        }
        query.skip((long) skip * limit);
        query.limit(limit);
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.find(query, News.class,"news");
    }
}
