package com.adl.portalcomercio.otp.infraestructure.adapter.repository;

import com.adl.portalcomercio.otp.domain.entity.OtpModel;
import com.adl.portalcomercio.otp.domain.repository.DBRepository;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.Random;

//@MicronautTest
public class DynamoDBRepositoryTest {

    @Inject
    DBRepository dbRepository;

    //@Test
    public void enhancedOtpValidationOk() throws Exception {
        // Arrange
        OtpModel otpModel = Mockito.mock(OtpModel.class);
        int anyIdCard = 1;
        Mockito.when(otpModel.getIdCard()).thenReturn(String.valueOf(anyIdCard));
        Mockito.when(otpModel.getIdCard()).thenReturn(String.valueOf(anyIdCard));

        // Act
        int attemptNumber = dbRepository.OtpValidation(otpModel);
        dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);

        // Assert
        Assertions.assertEquals(dbRepository.getAttemptNumber(String.valueOf(anyIdCard))-1, attemptNumber);
    }

    //@Test
    public void enhancedOtpLock() throws Exception {
        // Arrange
        OtpModel otpModel = Mockito.mock(OtpModel.class);
        int anyIdCard = (int)(Math.random()*10+1);
        Mockito.when(otpModel.getIdCard()).thenReturn(String.valueOf(anyIdCard));
        Mockito.when(otpModel.getIdCard()).thenReturn(String.valueOf(anyIdCard));

        // Act
        try {
            int attemptNumber = dbRepository.OtpValidation(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
            attemptNumber = dbRepository.OtpValidation(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
            attemptNumber = dbRepository.OtpValidation(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
            attemptNumber = dbRepository.OtpValidation(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
            attemptNumber = dbRepository.OtpValidation(otpModel);
            dbRepository.incrementNumberOtpCreated(otpModel, attemptNumber);
        } catch (Exception e) {
            // Assert
            Assertions.assertEquals("Superaste el numero maximo de solicitudes de codigo. Por favor verifica la cobertura de tu operador. Puedes intentar nuevamente en 1 minutos", e.getMessage());
        }
    }
}
