package com.adl.portalcomercio.otp.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryPasswordData {
    private String clientId;
    private String clientSecret;
    private String userName;
    private String confirmationCodeRecovery;
    private String newPassword;
}
