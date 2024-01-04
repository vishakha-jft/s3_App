package com.example.s3_app.controllers;

import com.example.s3_app.dtos.BucketDTO;
import com.example.s3_app.dtos.FolderDTO;
import com.example.s3_app.dtos.ImageDTO;
import com.example.s3_app.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/buckets")
public class StorageController {

    @Autowired
    private S3Service s3Service;
    @PostMapping()
    public ResponseEntity<Void> createBucket(@RequestBody BucketDTO bucketDTO){
        System.out.println("buckettttttn nameeeeeeeee"+bucketDTO.getBucketName());
//        s3Service.createBucket(bucketDTO.getBucketName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bucket-name}/folders")
    public ResponseEntity<Void> createFolder(@PathVariable("bucket-name")String bucketName,@RequestBody FolderDTO folderDTO) {
        System.out.println("folder Createdddd");
//        s3Service.createFolder(bucketName , folderDTO.getFolderName(), folderDTO.getPathName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bucket-name}/images")
    public ResponseEntity<Void>  uploadImage(@PathVariable("bucket-name")String bucketName, @RequestParam("image") MultipartFile file) throws IOException {
        System.out.println("image uploadddd");
        String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        s3Service.uploadImage(bucketName,file,uniqueFileName,"");
        return ResponseEntity.ok().build();
    }

}
