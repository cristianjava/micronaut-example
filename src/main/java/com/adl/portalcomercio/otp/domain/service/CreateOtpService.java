package com.adl.portalcomercio.otp.domain.service;

import com.adl.portalcomercio.otp.application.OtpData;
import com.adl.portalcomercio.otp.domain.entity.OtpModel;
import com.adl.portalcomercio.otp.domain.repository.DBRepository;
import com.adl.portalcomercio.otp.domain.repository.OtpRepository;

import javax.inject.Singleton;

@Singleton
public class CreateOtpService {

    private final OtpRepository otpRepository;
    private final DBRepository dbRepository;

    public CreateOtpService(OtpRepository otpRepository, DBRepository dbRepository) {
        this.otpRepository = otpRepository;
        this.dbRepository = dbRepository;
    }

    public String execute(OtpModel otpModel) throws Exception {
        int attemptNumber = dbRepository.OtpValidation(otpModel);
        if (attemptNumber > 0) {
            otpRepository.create(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
        }
        return "";
    }
}
