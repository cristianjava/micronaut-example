package com.adl.portalcomercio.otp.infraestructure.adapter.repository;

import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;
import com.adl.portalcomercio.otp.domain.repository.LoginRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@MicronautTest
public class CognitoRepositoryImplTest {

    private LoginRepository cognitoRepository;
    private RecoveryPasswordModel recoveryPasswordModel;

    private static final String CLIENT_ID = "48i3c27nrsofcbjq05ah65s0t0";
    private static final String CLIENT_SECRET = "qjek7rgttdd6k09v27fr4i77andcat3j5fe9b7okq8gko3mf0c8";
    private static final String USER_NAME = "cristian.93.dcg@gmail.com";
    private static final String NEW_PASSWORD = "Abcdef123*2";
    private static final String INVALID_CODE_MESSAGE = "Invalid verification code provided, please try again.";
    private static final String ATTEMPT_LIMIT = "Attempt limit exceeded, please try after some time.";

    @Test
    public void forgotPassword() {
        initialArrange();

        cognitoRepository.forgotPassword(recoveryPasswordModel);

        Mockito.verify(recoveryPasswordModel,Mockito.times(2)).getUserName();
    }


    @Test
    public void confirmForgotPassword() {
        initialArrange();
        Mockito.when(recoveryPasswordModel.getConfirmationCodeRecovery()).thenReturn(CLIENT_ID);
        Mockito.when(recoveryPasswordModel.getNewPassword()).thenReturn(NEW_PASSWORD);

        try {
            cognitoRepository.confirmForgotPassword(recoveryPasswordModel);
        } catch (Exception e) {
            Mockito.verify(recoveryPasswordModel,Mockito.times(2)).getUserName();
            Mockito.verify(recoveryPasswordModel,Mockito.times(1)).getConfirmationCodeRecovery();
        }
    }

    private void initialArrange() {
        cognitoRepository = new CognitoRepositoryImpl();
        recoveryPasswordModel = Mockito.mock(RecoveryPasswordModel.class);
        Mockito.when(recoveryPasswordModel.getClientId()).thenReturn(CLIENT_ID);
        Mockito.when(recoveryPasswordModel.getClientSecret()).thenReturn(CLIENT_SECRET);
        Mockito.when(recoveryPasswordModel.getUserName()).thenReturn(USER_NAME);
    }
}
