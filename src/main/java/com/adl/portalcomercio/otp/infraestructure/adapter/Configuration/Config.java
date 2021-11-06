package com.adl.portalcomercio.otp.infraestructure.adapter.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.env.Environment;

import java.util.Optional;

@Factory
public class Config {

    @Bean
    AmazonDynamoDBAsync dynamoDbAsyncClient(Environment environment) {
        Optional<String> secretKey = environment.get("aws.sns.secretId", String.class);
        Optional<String> accessKey = environment.get("aws.sns.accessKey", String.class);
        if (!secretKey.isPresent() || !accessKey.isPresent()) {
            throw new IllegalArgumentException("Aws credentials not provided");
        }
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey.get(), secretKey.get());
        AmazonDynamoDBAsyncClientBuilder clientBuilder = AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("dynamodb.sa-east-1.amazonaws.com", "sa-east-1")
                );

        return clientBuilder.build();
    }
}
