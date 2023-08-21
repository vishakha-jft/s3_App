package com.example.s3_app.controllers;

import com.example.s3_app.dtos.BucketDTO;
import com.example.s3_app.dtos.FolderDTO;
import com.example.s3_app.dtos.ImageDTO;
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
@RequestMapping("/buckets")
public class StorageController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/folders")
    public ResponseEntity<Void> createFolder(@RequestBody FolderDTO folderDTO) {
        s3Service.createFolder(folderDTO.getBucketName() , folderDTO.getFolderName(), folderDTO.getPathName());
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Void> createBucket(@RequestBody BucketDTO bucketDTO){
        s3Service.createBucket(bucketDTO.getBucketName());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/images")
    public ResponseEntity<Void>  uploadImage(@RequestBody ImageDTO imageDTO) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();
        s3Service.uploadImage(imageDTO.getPathName(), imageDTO.getImage(),uniqueFileName,"");
        System.out.println(imageDTO);
        return ResponseEntity.ok().build();
    }

}
