package com.example.demo4.request.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventsRequest {
    private String id;
    private String userId;
    @Size(max = 200, message = "Title's Characters is less than 200")
    private String title;
    private String banner;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date startTime;

    @JsonFormat(pattern="yyyy-mm-dd")
    private Date finishTime;
    private String content;
}
