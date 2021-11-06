package com.adl.portalcomercio.otp.domain.repository;

import com.adl.portalcomercio.otp.domain.entity.OtpModel;

public interface DBRepository {

    int OtpValidation(OtpModel otpModel) throws Exception;
    void incrementNumberOtpCreated(OtpModel otpModel, int attemptNumber);
    int getAttemptNumber(String idCard);
}
