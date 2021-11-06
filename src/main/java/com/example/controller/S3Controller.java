package com.example.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.example.services.IS3Service;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller("${controller.S3}")
public class S3Controller {

    private static final Logger LOG = LoggerFactory.getLogger(S3Controller.class);
    private final IS3Service s3Service;

    public S3Controller(IS3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Get("${controller.S3ListBuckets}")
    public List<String> listBuckets() {
        LOG.info("Inicio");
        return s3Service.ListBuckets();
    }

    @Put("${controller.S3CreateBuckets}")
    public Bucket createBucket(String bucketName) {
        return s3Service.createBucket(bucketName);
    }

    @Delete("${controller.S3DeleteBuckets}")
    public void deleteBucket(String bucketName) {
        s3Service.deleteBucket(bucketName);
    }

    @Put("${controller.S3PutObjectIntoBuckets}")
    public void putObject(String bucketName, String filePath) {
        s3Service.putObjectIntoBucket(bucketName, filePath);
    }
}
