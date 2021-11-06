package com.adl.portalcomercio.otp.domain.entity;

import lombok.Getter;

@Getter
public class RecoveryPasswordModel {

    private static final String SE_DEBE_INGRESAR_DATOS = "Se debe ingresar datos";

    private String clientId;
    private String clientSecret;
    private String userName;
    private String confirmationCodeRecovery;
    private String newPassword;

    public RecoveryPasswordModel(String clientId, String clientSecret, String userName, String confirmationCodeRecovery,
                                 String newPassword) throws Exception {
        validarObligatorio(userName, SE_DEBE_INGRESAR_DATOS);

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userName = userName;
        this.confirmationCodeRecovery = confirmationCodeRecovery;
        this.newPassword = newPassword;
    }

    private static void validarObligatorio(Object valor, String mensaje) throws Exception {
        if (valor == null) {
            throw new Exception(mensaje);
        }
    }

}
