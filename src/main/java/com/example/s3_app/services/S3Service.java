package com.example.s3_app.services;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String createBucket(String bucketName);
    String createFolder(String bucketName,String folderName,String prefix);
    String uploadFile(String bucketName, String folderName, MultipartFile file) throws IOException;
    String getImage(String bucketName,String imageName,String prefix) throws IOException;
    List<String> getImages(String bucketName,String prefix);
    void uploadImage(String bucketName, String image,String key, String imagePath) throws  IOException;
    List<String> getFolders(String bucketName,String Prefix);
    List<String> getBucketList();
}
