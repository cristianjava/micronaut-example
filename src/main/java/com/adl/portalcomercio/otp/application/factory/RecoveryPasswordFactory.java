package com.adl.portalcomercio.otp.application.factory;

import com.adl.portalcomercio.otp.application.RecoveryPasswordData;
import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;

import javax.inject.Singleton;

@Singleton
public class RecoveryPasswordFactory {

    public RecoveryPasswordModel create(RecoveryPasswordData recoveryPasswordData) throws Exception {
        return new RecoveryPasswordModel(recoveryPasswordData.getClientId(),
                recoveryPasswordData.getClientSecret(),
                recoveryPasswordData.getUserName(),
                recoveryPasswordData.getConfirmationCodeRecovery(),
                recoveryPasswordData.getNewPassword());
    }
}
