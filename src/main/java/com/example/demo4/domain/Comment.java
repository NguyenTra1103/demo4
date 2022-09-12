package com.example.demo4.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    private String id;
    private String userId;
    private String content;
    private String createTime;
    private String newsId;
    private int countSubComment;
    private List<SubComment> subComments = new ArrayList<>();
}