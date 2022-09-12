
package com.example.demo4.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "news")
public class News {
    @Id
    private String id;
    private String userId;
    private String updateId;
    @Size(max = 200)
    private String title;
    private String content;
    private Long lastUpdateTime;
    private Long lastCreateTime;
    private String hashTag;
    private String banner;
    private List<Comment> comments = new ArrayList<>();
}
