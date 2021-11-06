package com.example.application.handler;

import com.example.domain.portalComercio.otp.service.CreateOtpService;

import javax.inject.Singleton;

@Singleton
public class CreateOtpHandler {

    private CreateOtpService createOtpService;

    public CreateOtpHandler(CreateOtpService createOtpService) {
        this.createOtpService = createOtpService;
    }

    public String otpCreate() {
        return createOtpService.execute();
    }
}
