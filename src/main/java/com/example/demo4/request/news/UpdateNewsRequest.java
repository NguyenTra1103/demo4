package com.example.demo4.request.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsRequest {
    private String id;
    private String createId;
    private String updateId;
    @Size(max = 200,message = "Title's character is less than 200")
    private String title;
    private String content;
    private String hashTag;
    private String banner;
    private Long lastUpTime;
}
