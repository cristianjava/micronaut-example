package com.example;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

//@MicronautTest
public class HelloWorldControllerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/hello")
    RxHttpClient client;

    //@Test
    void testItWorks () {
        Assertions.assertTrue(application.isRunning());
    }

    //@Test
    void testHelloWorldResponse() {
        final String result = client.toBlocking().retrieve("");
        Assertions.assertEquals("Hello World", result);
    }
}
