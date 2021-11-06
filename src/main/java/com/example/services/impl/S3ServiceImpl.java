package com.example.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.services.IS3Service;

import javax.inject.Singleton;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Singleton
public class S3ServiceImpl implements IS3Service {

    public List<String> ListBuckets () {
        List<String> listBucketsReturn = new ArrayList<>();
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");
        AWSCredentialsProvider basicAWSCredentials = new AWSStaticCredentialsProvider(awsCredentials);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");

        final AmazonS3 s31 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.US_WEST_2)
                .build();
        ListObjectsV2Result result = s31.listObjectsV2("s3elasticbeanstalk-us-east-2-396003076446");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
            listBucketsReturn.add(os.getKey());
        }
        return listBucketsReturn;
    }


    public Bucket createBucket(String bucketName) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.DEFAULT_REGION)
                .build();
        Bucket b = null;
        if (s3.doesBucketExistV2(bucketName)) {
            System.out.format("Bucket %s already exists.\n", bucketName);
            b = getBucket(bucketName);
        } else {
            try {
                b = s3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }

    private static Bucket getBucket(String bucket_name) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.US_EAST_2)
                .build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public void deleteBucket(String bucketName) {

        System.out.println("Deleting S3 bucket: " + bucketName);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");
        final AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.DEFAULT_REGION)
                .build();
        try {
            System.out.println(" - removing objects from bucket");
            ObjectListing object_listing = s3.listObjects(bucketName);
            while (true) {
                for (Iterator<?> iterator =
                     object_listing.getObjectSummaries().iterator();
                     iterator.hasNext(); ) {
                    S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
                    s3.deleteObject(bucketName, summary.getKey());
                }

                // more object_listing to retrieve?
                if (object_listing.isTruncated()) {
                    object_listing = s3.listNextBatchOfObjects(object_listing);
                } else {
                    break;
                }
            }

            System.out.println(" - removing versions from bucket");
            VersionListing version_listing = s3.listVersions(
                    new ListVersionsRequest().withBucketName(bucketName));
            while (true) {
                for (Iterator<?> iterator =
                     version_listing.getVersionSummaries().iterator();
                     iterator.hasNext(); ) {
                    S3VersionSummary vs = (S3VersionSummary) iterator.next();
                    s3.deleteVersion(
                            bucketName, vs.getKey(), vs.getVersionId());
                }

                if (version_listing.isTruncated()) {
                    version_listing = s3.listNextBatchOfVersions(
                            version_listing);
                } else {
                    break;
                }
            }

            System.out.println(" OK, bucket ready to delete!");
            s3.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
     }

     public void putObjectIntoBucket(String bucketName, String filePath) {
         String bucket_name = bucketName;
         String file_path = filePath;
         String key_name = Paths.get(file_path).getFileName().toString();

         System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
         BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAVYM47NFPABW6E6VS", "ZHir8g6WMTVaz1abFaYP6Y8u67i+qrXIjb+N8blZ");
         final AmazonS3 s3 = AmazonS3ClientBuilder
                 .standard()
                 .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                 .withRegion(Regions.US_EAST_2)
                 .build();
         try {
             s3.putObject(bucket_name, key_name, new File(file_path));
         } catch (AmazonServiceException e) {
             System.err.println(e.getErrorMessage());
             System.exit(1);
         }
         System.out.println("Done!");
     }

}
