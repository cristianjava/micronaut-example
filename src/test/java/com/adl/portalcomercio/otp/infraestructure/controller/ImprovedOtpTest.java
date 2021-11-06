package com.adl.portalcomercio.otp.infraestructure.controller;

import com.adl.portalcomercio.otp.application.OtpData;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

//@MicronautTest
public class ImprovedOtpTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/otp/create")
    RxHttpClient client;

    @Inject
    ImprovedOtp improvedOtp;

    //@Test
    void testHelloWorldResponse() throws Exception {
        improvedOtp.OtpCreate(new OtpData("1","1"));

    }
}

