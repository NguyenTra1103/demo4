package com.example.demo4.response.request;

import com.example.demo4.contant.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponse {
    private String id;
    private String requestTitle;
    private RequestStatus requestStatus;
    private String timeRequest;
    private String timeTotal;
    private String timeRemain;
}

