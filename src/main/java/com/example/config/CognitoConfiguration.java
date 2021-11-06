package com.example.config;

import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.env.Environment;

import java.util.Optional;

@Factory
public class CognitoConfiguration {

    private final static String ACCESS_KEY = "aws.cognito.access-key-id";
    private final static String AWS_ACCESS_KEY = "aws.accessKeyId";
    private final static String SECRET_KEY = "aws.cognito.secret-access-key";
    private final static String AWS_SECRET_KEY = "aws.secretKey";
    private final static String CREDENTIALS_NO_PROVIDED = "Aws credentials not provided";

    @Bean
    AWSCognitoIdentityProvider cognitoIdentityProviderClient(Environment environment) {
        Optional<String> accessKey = environment.get(ACCESS_KEY, String.class);
        Optional<String> secretKey = environment.get(SECRET_KEY, String.class);
        if (!secretKey.isPresent() || !accessKey.isPresent()) {
            throw new IllegalArgumentException(CREDENTIALS_NO_PROVIDED);
        }
        System.setProperty(AWS_ACCESS_KEY, accessKey.get());
        System.setProperty(AWS_SECRET_KEY, secretKey.get());
        AWSCognitoIdentityProvider client = AWSCognitoIdentityProviderClientBuilder.standard()
            .withRegion(Regions.US_EAST_2)
            .withCredentials(new SystemPropertiesCredentialsProvider())
            .build();
        return client;
    }

}
