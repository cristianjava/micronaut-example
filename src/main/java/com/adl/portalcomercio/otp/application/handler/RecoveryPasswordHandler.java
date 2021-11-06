package com.adl.portalcomercio.otp.application.handler;

import com.adl.portalcomercio.otp.application.RecoveryPasswordData;
import com.adl.portalcomercio.otp.application.factory.RecoveryPasswordFactory;
import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;
import com.adl.portalcomercio.otp.domain.service.RecoveryPasswordService;

import javax.inject.Singleton;

@Singleton
public class RecoveryPasswordHandler {

    private RecoveryPasswordFactory recoveryPasswordFactory;
    private RecoveryPasswordService recoveryPasswordService;

    public RecoveryPasswordHandler(RecoveryPasswordFactory recoveryPasswordFactory, RecoveryPasswordService recoveryPasswordService) {
        this.recoveryPasswordFactory = recoveryPasswordFactory;
        this.recoveryPasswordService = recoveryPasswordService;
    }

    public String execute(RecoveryPasswordData recoveryPasswordData) throws Exception {
        RecoveryPasswordModel recoveryPasswordModel = this.recoveryPasswordFactory.create(recoveryPasswordData);
        return recoveryPasswordService.execute(recoveryPasswordModel);
    }

    public String executeConfirm(RecoveryPasswordData recoveryPasswordData) throws Exception {
        RecoveryPasswordModel recoveryPasswordModel = this.recoveryPasswordFactory.create(recoveryPasswordData);
        return recoveryPasswordService.executeConfirm(recoveryPasswordModel);
    }
}
