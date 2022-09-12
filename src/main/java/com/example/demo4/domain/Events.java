package com.example.demo4.domain;

import com.example.demo4.contant.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(collection = "events")
@Builder
public class Events {
    @Id
    private String id;
    @Size(max = 200)
    private String title;
    private Status status;
    private String banner;
    @JsonFormat(pattern="yyyy-mm-dd hh:mm:ss")
    private String startTime;
    private String finishTime;
    private String content;

}
