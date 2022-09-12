package com.example.demo4.response;

import com.example.demo4.contant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsSearchResponse {
    private String title;
    private String startTime;
    private String finishTime;
    private Status status;
}
