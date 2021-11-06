package com.example.services;

import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public interface IS3Service {

    List<String> ListBuckets();
    Bucket createBucket(String bucketName);
    void deleteBucket(String bucketName);
    void putObjectIntoBucket(String bucketName, String filePath);
}
