package com.example.demo4.request.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventsRequest {
    private String id;
    private String userId;
    @Size(max = 200, message = "Title's Characters is less than 200")
    private String title;
    private String banner;
    private String startTime;
    private String finishTime;
    private String content;
}
