package com.example.s3_app.controllers;
import com.example.s3_app.services.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/")
    public String home(Model model) {
        List <String> bucketList = s3Service.getBucketList();
        model.addAttribute("bucketList",bucketList);
        return "home";
    }

    @GetMapping("/buckets/{bucket-name}/**")
    public String getFolders(@PathVariable("bucket-name") String bucketName, HttpServletRequest request, Model model) {
        String remainingPath = (String) request.getAttribute(org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String prefix = "/buckets/" + bucketName + "/";
        String dataAfterPrefix = "";
        if (remainingPath.startsWith(prefix)) {
            dataAfterPrefix =  remainingPath.substring(prefix.length());
        }
        List <String> folderList1 = s3Service.getFolders(bucketName,dataAfterPrefix);
        model.addAttribute("bucketName",bucketName);
        model.addAttribute("folderList",folderList1);
        return "folderList";
    }

    @GetMapping("/buckets/{bucket-name}/images/**")
    public ResponseEntity<List<String>> getAllImageUrls(@PathVariable("bucket-name") String bucketName, HttpServletRequest request) throws IOException {
        String remainingPath = (String) request.getAttribute(org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String prefix = "/buckets/" + bucketName + "/images/";
        String dataAfterPrefix = "";
        if (remainingPath.startsWith(prefix)) {
            dataAfterPrefix =  remainingPath.substring(prefix.length());
        }
        System.out.println(" prefixxxx "+ dataAfterPrefix);
        List <String> imageList = s3Service.getImages(bucketName,dataAfterPrefix);
        List <String> imageurls = new ArrayList<>();
        for (String image : imageList ) {
            imageurls.add(s3Service.getImage(bucketName,image,dataAfterPrefix));
        }
        return ResponseEntity.ok(imageurls);
    }
}
