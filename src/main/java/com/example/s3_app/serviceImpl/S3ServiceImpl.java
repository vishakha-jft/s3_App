package com.example.s3_app.serviceImpl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.s3_app.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {
    private AmazonS3 amazonS3;

    @Autowired
    public  S3ServiceImpl(AmazonS3 amazonS3Client){
        this.amazonS3 =amazonS3Client;
    }
    @Override
    public String createFolder(String bucketName,String folderName,String prefix) {
        System.out.println("in create folder   "+prefix);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, prefix+folderName , emptyContent, metadata);
        amazonS3.putObject(putObjectRequest);
        return folderName;
    }

    @Override
    public String uploadFile(String bucketName, String folderName, MultipartFile file) throws IOException {
    String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        return fileName;
    }

    @Override
    public String getImage(String bucketName, String imageName,String prefix) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, prefix+imageName);
        byte[] imageBytes = amazonS3.getObject(getObjectRequest).getObjectContent().readAllBytes();
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        return imageBase64;
    }

    @Override
    public List<String> getImages(String bucketName,String prefix) {
        List<String> images = new ArrayList<>();
        ObjectListing objectListing = amazonS3.listObjects(bucketName,prefix);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            if (objectSummary.getKey().endsWith(".png") ){
                images.add(objectSummary.getKey());
            }
        }
        return images;
    }

    @Override
    public List<String> getFolders(String bucketName,String prefix) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName,prefix);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        List<String> folders = new ArrayList<>();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            if (!objectSummary.getKey().endsWith(".png")) {
                folders.add(objectSummary.getKey());
            }
        }
        return folders;
    }
    public void uploadImage(String bucketName, String image, String key, String imagePath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(image);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.setContentLength(imageBytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
    }

    @Override
    public String createBucket(String bucketName) {
        if(amazonS3.doesBucketExist(bucketName)) {
            return null;
        }
        Bucket az = amazonS3.createBucket(bucketName);
        return bucketName;
    }

    public List<String> getBucketList() {
        List<Bucket> bucketList = amazonS3.listBuckets();
        List<String> bucketNames = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketNames.add(bucket.getName());
        }

        return bucketNames;
    }

}
