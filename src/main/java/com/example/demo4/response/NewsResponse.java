
package com.example.demo4.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponse {
    private String id;
    private String banner;
    private String title;
    private String content;
    private String hashtag;
}
