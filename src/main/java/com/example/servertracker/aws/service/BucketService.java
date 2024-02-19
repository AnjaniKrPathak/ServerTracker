package com.example.servertracker.aws.service;

public interface BucketService {
    public void createBucket();
    public void deleteBucket(String bucket);
    public String uploadObject(String bucket,String key);
}
