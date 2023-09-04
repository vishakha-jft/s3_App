package com.example.s3_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    public String bucketName;
    public String pathName;
    public String image;
}
