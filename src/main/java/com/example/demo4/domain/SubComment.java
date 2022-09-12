package com.example.demo4.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubComment {
    @Id
    private String id;
    private String userId;
    private String content;
    private String createTime;
    private String newsId;
    private String commentId;
}
