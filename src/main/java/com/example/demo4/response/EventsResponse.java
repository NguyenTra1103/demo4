package com.example.demo4.response;

import com.example.demo4.contant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventsResponse {
    private int code;
    private String message;
    private Object data;
}
