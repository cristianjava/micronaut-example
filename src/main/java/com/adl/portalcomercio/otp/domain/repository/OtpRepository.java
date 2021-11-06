package com.adl.portalcomercio.otp.domain.repository;

import com.adl.portalcomercio.otp.domain.entity.OtpModel;

public interface OtpRepository {

    Long create(OtpModel otpModel);
}
