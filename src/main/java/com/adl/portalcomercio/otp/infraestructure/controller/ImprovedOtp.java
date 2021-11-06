package com.adl.portalcomercio.otp.infraestructure.controller;

import com.adl.portalcomercio.otp.application.OtpData;
import com.adl.portalcomercio.otp.application.handler.CreateOtpHandler;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.RequestAttribute;
import io.micronaut.http.annotation.RequestBean;

@Controller("/otp")
public class ImprovedOtp {

    private CreateOtpHandler otpCreate;

    public ImprovedOtp(CreateOtpHandler otpCreate) {
        this.otpCreate = otpCreate;
    }

    @Post("/create")
    public String OtpCreate(OtpData otpData) throws Exception {
        return otpCreate.execute(otpData);
    }

    //enhanced
}
