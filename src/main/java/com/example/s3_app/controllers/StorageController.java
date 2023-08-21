package com.example.s3_app.controllers;

import com.example.s3_app.dtos.BucketDTO;
import com.example.s3_app.dtos.FolderDTO;
import com.example.s3_app.dtos.ImageDTO;
import com.example.s3_app.services.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/buckets")
public class StorageController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/{bucket-name}/folders")
    public ResponseEntity<Void> createFolder(@PathVariable("bucket-name")String bucketName,@RequestBody FolderDTO folderDTO) {
        s3Service.createFolder(bucketName , folderDTO.getFolderName(), folderDTO.getPathName());
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Void> createBucket(@RequestBody BucketDTO bucketDTO){
        s3Service.createBucket(bucketDTO.getBucketName());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{bucket-name}/images")
    public ResponseEntity<Void>  uploadImage(@PathVariable("bucket-name")String bucketName,@RequestBody ImageDTO imageDTO) throws IOException {
        log.info(bucketName);
        String uniqueFileName = UUID.randomUUID().toString();
        s3Service.uploadImage(imageDTO.getPathName(), imageDTO.getImage(),uniqueFileName,"");
        System.out.println(imageDTO);
        return ResponseEntity.ok().build();
    }

}
