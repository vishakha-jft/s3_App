package com.example.s3_app.controllers;

import com.example.s3_app.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import java.io.IOException;

@RestController
@RequestMapping("/bucket")
public class StorageController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/folders")
    public ResponseEntity<Void> createFolder(@RequestParam("bucketName")String bucketName,@RequestParam("pathName")String pathName,@RequestParam("folderName")String folderName) {
        String foldername = s3Service.createFolder(bucketName , folderName,pathName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bucket-name}")
    public ResponseEntity<Void> createBucket(@PathVariable("bucket-name") String bucketName){
        String bucket = s3Service.createBucket(bucketName);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/images")
    public ResponseEntity<byte[]>  uploadImage(@RequestParam("image") MultipartFile file,@RequestParam("pathName") String pathName) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        s3Service.uploadImage(pathName,file,uniqueFileName,"");
        MultipartFile file1 = file;
        byte [ ] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }

}
