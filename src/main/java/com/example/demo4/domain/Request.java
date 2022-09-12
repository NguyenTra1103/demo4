package com.example.demo4.domain;

import com.example.demo4.contant.RequestDelayType;
import com.example.demo4.contant.RequestStatus;
import com.example.demo4.contant.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "request")
@Builder
public class Request {
    @Id
    private String id;
    private String userId;
    private String userName;
    private RequestType requestType;
    private String requestTitle;
    private String content;
    private String receiverEmail;
    private RequestStatus requestStatus;
    private double totalDay;
    private String dayRequest;
    private String timeDelay;
    private String createRequestTime;
    private int remainTime;
    private int yearRequest;
}
