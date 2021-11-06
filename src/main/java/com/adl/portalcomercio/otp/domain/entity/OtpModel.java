package com.adl.portalcomercio.otp.domain.entity;

import lombok.Getter;

@Getter
public class OtpModel {

    private static final String SE_DEBE_INGRESAR_LA_CLAVE = "Se debe ingresar la clave";

    private String idCard;
    private String phoneNumber;

    public OtpModel(String idCard, String phoneNumber) throws Exception {
        validarObligatorio(idCard, SE_DEBE_INGRESAR_LA_CLAVE);

        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
    }

    private static void validarObligatorio(Object valor, String mensaje) throws Exception {
        if (valor == null) {
            throw new Exception(mensaje);
        }
    }

}
