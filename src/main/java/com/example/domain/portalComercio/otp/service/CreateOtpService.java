package com.example.domain.portalComercio.otp.service;

import com.example.domain.portalComercio.otp.model.entity.OtpModel;
import com.example.domain.portalComercio.otp.repository.DBRepository;
import com.example.domain.portalComercio.otp.repository.OtpRepository;

import javax.inject.Singleton;

@Singleton
public class CreateOtpService {

    private OtpRepository otpRepository;
    private DBRepository dbRepository;

    public CreateOtpService(OtpRepository otpRepository, DBRepository dbRepository) {
        this.otpRepository = otpRepository;
        this.dbRepository = dbRepository;
    }

    public String execute(){
        //validaciones
        if (dbRepository.OtpValidation()) {
            otpRepository.create(new OtpModel());
        }
        return "";
    }
}
