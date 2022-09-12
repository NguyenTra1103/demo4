package com.example.demo4.request.subcomment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubCommentRequest {
    private String content;
    private String createTime;
    private String newsId;
    private String commentId;
}
