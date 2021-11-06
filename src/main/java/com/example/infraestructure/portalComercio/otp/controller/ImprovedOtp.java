package com.example.infraestructure.portalComercio.otp.controller;

import com.adl.portalcomercio.otp.application.OtpData;
import com.adl.portalcomercio.otp.application.handler.CreateOtpHandler;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/otp")
public class ImprovedOtp {

    public ImprovedOtp(CreateOtpHandler otpCreate) {
        this.otpCreate = otpCreate;
    }

    private CreateOtpHandler otpCreate;

    @Post("/badCreate")
    public String OtpCreate() throws Exception {
        return otpCreate.execute(new OtpData());
    }
}
