package com.example.services;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class HelloWorldService {

    @Property(name = "constant.hello", defaultValue = "${constant.default}")
    private String proofTwo;

    @Value("${constant.hello}")
    private String helloWorld;

    public String hello() {
        return helloWorld;
    }
}
