package com.example.util;

import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

/**
 * List your Amazon S3 buckets.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */

@Singleton
public class ListBuckets {

    @Value("${aws.sns.accessKey}")
    private String accessKey;
    @Value("${aws.sns.secretId}")
    private String secretId;
/**
    public static void main(String[] args) {
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAVYM47NFPIX75G4MK", "jdJsm4EXIGJPbQyxdSDvE1yzQt+ko93SQZsrjGQS");
        AWSCredentialsProvider basicAWSCredentials = new AWSStaticCredentialsProvider(awsCredentials);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.DEFAULT_REGION)
                .withCredentials(basicAWSCredentials)
                .build();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
    }*/
}