package com.example.demo4.request.requests;

import com.example.demo4.contant.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    private String id;
    private RequestStatus requestStatus;
}
