package com.adl.portalcomercio.otp.application.handler;

import com.adl.portalcomercio.otp.application.OtpData;
import com.adl.portalcomercio.otp.application.factory.OtpFactory;
import com.adl.portalcomercio.otp.domain.entity.OtpModel;
import com.adl.portalcomercio.otp.domain.service.CreateOtpService;

import javax.inject.Singleton;

@Singleton
public class CreateOtpHandler {

    private CreateOtpService createOtpService;
    private OtpFactory otpFactory;

    public CreateOtpHandler(CreateOtpService createOtpService, OtpFactory otpFactory) {
        this.createOtpService = createOtpService;
        this.otpFactory = otpFactory;
    }

    public String execute(OtpData otpData) throws Exception {
        OtpModel otpModel = this.otpFactory.crear(otpData);
        return createOtpService.execute(otpModel);
    }
}
