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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern imagePattern = Pattern.compile("^.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$");
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String objectKey = objectSummary.getKey();
            Matcher matcher = imagePattern.matcher(objectKey);

            if (matcher.matches()) {
                images.add(objectKey);
            }
        }
        System.out.println("imagess"+images.size());
        return images;
    }

    @Override
    public List<String> getFolders(String bucketName,String prefix) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName,prefix);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        List<String> folders = new ArrayList<>();
        Pattern imagePattern = Pattern.compile(".+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$");
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String objectKey = objectSummary.getKey();
            Matcher matcher = imagePattern.matcher(objectKey);
            if (!matcher.matches()) {
                folders.add(objectKey);
            }
        }
        return folders;
    }
    public void uploadImage(String bucketName, MultipartFile image, String key, String imagePath) throws IOException {
        System.out.println(" uploaddd image impl");
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        InputStream inputStream = image.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
        System.out.println(" upload image successsss ");
    }

    @Override
    public String createBucket(String bucketName) {
        if(amazonS3.doesBucketExist(bucketName)) {
            System.out.println(" Bucket Existtttt ");
            return null;
        }
        Bucket az = amazonS3.createBucket(bucketName);
        System.out.println("bucket Createddddd");
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
