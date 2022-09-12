package com.example.demo4.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "upload")
@Builder
public class UploadFile {
    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
