package com.adl.portalcomercio.otp.infraestructure.controller;

import com.adl.portalcomercio.otp.application.RecoveryPasswordData;
import com.adl.portalcomercio.otp.application.handler.RecoveryPasswordHandler;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@MicronautTest
public class RecoveryPasswordControllerTest {

    private RecoveryPasswordController recoveryPasswordController;
    private RecoveryPasswordHandler recoveryPasswordHandler;
    private RecoveryPasswordData recoveryPasswordData;

    private static final String CODE_DELIVERY_SENT = "code was delivery";
    private static final String ERROR_MESSAGE = "Error Message";


    @Test
    public void testRecoveryPasswordControllerOk() throws Exception {
        arrangeInstances();
        Mockito.when(recoveryPasswordHandler.execute(recoveryPasswordData)).thenReturn(CODE_DELIVERY_SENT);

        String result = recoveryPasswordController.forgotPassword(recoveryPasswordData);

        Assertions.assertEquals(result, CODE_DELIVERY_SENT);
    }


    @Test
    public void testRecoveryPasswordControllerException() throws Exception {
        arrangeInstances();
        Mockito.when(recoveryPasswordHandler.execute(recoveryPasswordData)).thenThrow(new Exception(ERROR_MESSAGE));

        try {
            String result = recoveryPasswordController.forgotPassword(recoveryPasswordData);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), ERROR_MESSAGE);
        }
    }

    private void arrangeInstances() {
        recoveryPasswordHandler = Mockito.mock(RecoveryPasswordHandler.class);
        recoveryPasswordData = Mockito.mock(RecoveryPasswordData.class);
        recoveryPasswordController = new RecoveryPasswordController(recoveryPasswordHandler);
    }
}
