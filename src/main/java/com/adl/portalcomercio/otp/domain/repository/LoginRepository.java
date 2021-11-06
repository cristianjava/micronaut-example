package com.adl.portalcomercio.otp.domain.repository;

import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;

public interface LoginRepository {

    String confirmPassword(RecoveryPasswordModel passwordModel);
    String forgotPassword(RecoveryPasswordModel recoveryPasswordModel);
    String confirmForgotPassword(RecoveryPasswordModel recoveryPasswordModel);
}
