package com.adl.portalcomercio.otp.domain.service;

import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;
import com.adl.portalcomercio.otp.domain.repository.LoginRepository;

import javax.inject.Singleton;

@Singleton
public class RecoveryPasswordService {

    private LoginRepository loginRepository;

    public RecoveryPasswordService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public String execute(RecoveryPasswordModel recoveryPasswordModel) {
        return loginRepository.forgotPassword(recoveryPasswordModel);
    }

    public String executeConfirm(RecoveryPasswordModel recoveryPasswordModel) {
        return loginRepository.confirmForgotPassword(recoveryPasswordModel);
    }
}
