package com.adl.portalcomercio.otp.infraestructure.controller;

import com.adl.portalcomercio.otp.application.RecoveryPasswordData;
import com.adl.portalcomercio.otp.application.handler.RecoveryPasswordHandler;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/recoveryPassword")
public class RecoveryPasswordController {

    RecoveryPasswordHandler recoveryPasswordHandler;

    public RecoveryPasswordController(RecoveryPasswordHandler recoveryPasswordHandler) {
        this.recoveryPasswordHandler = recoveryPasswordHandler;
    }

    @Post("/forgotPassword")
    public String forgotPassword(RecoveryPasswordData recoveryPasswordData) throws Exception {
        return recoveryPasswordHandler.execute(recoveryPasswordData);
    }

    @Post("/confirmForgotPassword")
    public String confirmForgotPassword(RecoveryPasswordData recoveryPasswordData) throws Exception {
        return recoveryPasswordHandler.executeConfirm(recoveryPasswordData);
    }
}
