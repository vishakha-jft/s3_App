package com.example.s3_app.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDTO {
    public String bucketName;
    public String pathName;
    public String folderName;
}