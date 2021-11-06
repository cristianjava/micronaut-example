package com.adl.portalcomercio.otp.application.factory;

import com.adl.portalcomercio.otp.application.OtpData;
import com.adl.portalcomercio.otp.domain.entity.OtpModel;

import javax.inject.Singleton;

@Singleton
public class OtpFactory {

    public OtpModel crear(OtpData otpData) throws Exception {
        return new OtpModel(
                otpData.getIdCard(),
                otpData.getPhoneNumber()
        );
    }
}
