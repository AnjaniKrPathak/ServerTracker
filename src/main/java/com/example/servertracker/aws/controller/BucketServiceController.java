package com.example.servertracker.aws.controller;

import com.example.servertracker.aws.data.BucketObject;
import com.example.servertracker.aws.service.BucketService;
import com.example.servertracker.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BucketServiceController {
    @Autowired
    BucketService bucketService;
    @Autowired
    ReportService reportService;
    @GetMapping("/createBucket")
    public String createBucket(){
        bucketService.createBucket();
        return "Bucket Created";


    }
    @DeleteMapping("/{bucket}")
    public String deleteBucket(@PathVariable String bucket){
        bucketService.deleteBucket(bucket);
        return "Bucket Deleted Succussfully";

    }
    @PostMapping("/uploadBucketObject")
    public String uploadBucketObject(@RequestBody BucketObject bucket){
        reportService.genReport();
       String url= bucketService.uploadObject(bucket.getBucketName(),bucket.getPath());


        return "Object Uploaded, S3 Bucket URL :  "+ url;
    }

}
