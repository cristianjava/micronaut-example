package com.adl.portalcomercio.otp.domain.service;

import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;
import com.adl.portalcomercio.otp.domain.repository.LoginRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@MicronautTest
public class RecoveryPasswordServiceTest {

    private RecoveryPasswordService recoveryPasswordService;
    private RecoveryPasswordModel recoveryPasswordModel;
    private LoginRepository loginRepository;

    private static final String CODE_DELIVERY_SENT = "code was delivery";

    @Test
    public void executeOk() {
        arrangeInstances();
        Mockito.when(loginRepository.forgotPassword(recoveryPasswordModel)).thenReturn(CODE_DELIVERY_SENT);

        String result = recoveryPasswordService.execute(recoveryPasswordModel);

        Assertions.assertEquals(result, CODE_DELIVERY_SENT);
    }

    @Test
    public void executeConfirm() {
        arrangeInstances();
        Mockito.when(loginRepository.confirmForgotPassword(recoveryPasswordModel)).thenReturn(CODE_DELIVERY_SENT);

        String result = recoveryPasswordService.executeConfirm(recoveryPasswordModel);

        Assertions.assertEquals(result, CODE_DELIVERY_SENT);
    }

    private void arrangeInstances() {
        loginRepository = Mockito.mock(LoginRepository.class);
        recoveryPasswordModel = Mockito.mock(RecoveryPasswordModel.class);
        recoveryPasswordService = new RecoveryPasswordService(loginRepository);
    }
}
