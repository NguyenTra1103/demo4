
package com.example.demo4.request.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {
    private String id;
    private String userId;
    private String updateId;
    @Size(max = 200, message = "Title's Characters is less than 200")
    private String title;
    private String content;
    private String lastUpdateTime;
    private Long lastCreateTime;
    private String hashTag;
    private String banner;
    private String comment;
}
