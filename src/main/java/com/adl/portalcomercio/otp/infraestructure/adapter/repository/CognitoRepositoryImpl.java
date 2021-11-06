package com.adl.portalcomercio.otp.infraestructure.adapter.repository;

import com.adl.portalcomercio.otp.domain.entity.RecoveryPasswordModel;
import com.adl.portalcomercio.otp.domain.repository.LoginRepository;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Singleton
public class CognitoRepositoryImpl implements LoginRepository {

    AwsBasicCredentials awsCred = AwsBasicCredentials.create("AKIAVYM47NFPNMOQGQRJ", "9PIG0K4Uy/wUPMRsk+A+UHcD0Jj0fPo/agy6mZ29");
    CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCred))
            .region(Region.US_EAST_2)
            .build();

    public String confirmPassword(RecoveryPasswordModel passwordModel) {
        AdminSetUserPasswordRequest adminSetUserPasswordRequest = AdminSetUserPasswordRequest.builder()
                .password(passwordModel.getNewPassword())
                .permanent(true)
                .username(passwordModel.getUserName())
                .userPoolId("us-east-2_qb9ks3Rx3")
                .build();
        AdminSetUserPasswordResponse adminSetUserPasswordResponse = cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
        return adminSetUserPasswordResponse.toString();
    }

    @Override
    public String forgotPassword(RecoveryPasswordModel recoveryPasswordModel) {
        ForgotPasswordRequest forgotPasswordRequest = ForgotPasswordRequest.builder()
                .clientId(recoveryPasswordModel.getClientId())
                .username(recoveryPasswordModel.getUserName())
                .secretHash(calculateSecretHash(recoveryPasswordModel.getClientId(),
                        recoveryPasswordModel.getClientSecret(),
                        recoveryPasswordModel.getUserName()))
                .build();
        ForgotPasswordResponse response1 = cognitoClient.forgotPassword(forgotPasswordRequest);
        return response1.codeDeliveryDetails().toString();
    }

    @Override
    public String confirmForgotPassword(RecoveryPasswordModel recoveryPasswordModel) {
        ConfirmForgotPasswordRequest confirmForgotPasswordRequest = ConfirmForgotPasswordRequest.builder()
                .clientId(recoveryPasswordModel.getClientId())
                .username(recoveryPasswordModel.getUserName())
                .secretHash(calculateSecretHash(recoveryPasswordModel.getClientId(),
                        recoveryPasswordModel.getClientSecret(),
                        recoveryPasswordModel.getUserName()))
                .confirmationCode(recoveryPasswordModel.getConfirmationCodeRecovery())
                .password(recoveryPasswordModel.getNewPassword())
                .build();
        ConfirmForgotPasswordResponse responseConfirmForgotPasswordResponse = cognitoClient.confirmForgotPassword(confirmForgotPasswordRequest);
        return responseConfirmForgotPasswordResponse.sdkHttpResponse().statusText().toString();
    }

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
    }
}
