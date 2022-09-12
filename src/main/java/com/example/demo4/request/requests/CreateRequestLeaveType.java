package com.example.demo4.request.requests;

import com.example.demo4.contant.Reason;
import com.example.demo4.contant.RequestDelayType;
import com.example.demo4.contant.RequestLeaveType;
import com.example.demo4.contant.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRequestLeaveType {
    @NotBlank(message = "Required email must not be blank or null")
    private String requiredEmail;

    private RequestLeaveType requestLeaveType;

    @NotBlank(message = "Day start must not be blank")
    private String dayStart;
    private Shift shiftStart;

    @NotBlank(message = "Day end must not be blank")
    private String dayEnd;
    private Shift shiftEnd;

    private Reason reason;

    @NotBlank(message = "Content must not be blank or null")
    private String content;

}
