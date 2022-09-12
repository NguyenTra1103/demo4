package com.example.demo4.request.requests;

import com.example.demo4.contant.Reason;
import com.example.demo4.contant.RequestDelayType;
import com.example.demo4.contant.Shift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDelayType {
    @NotBlank(message = "Required email must not be blank or null")
    private String requiredEmail;

    private RequestDelayType requestDelayType;

    private String timeLateOrEarly;

    @NotBlank(message = "Day request must not be blank")
    private String dayRequest;

    private Shift shift;

    private Reason reason;

    @NotBlank(message = "Content must not be blank or null")
    private String content;




}
