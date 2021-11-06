package com.example.infraestructure.portalComercio.otp.adapter.repository;

import com.example.domain.portalComercio.otp.repository.DBRepository;

import javax.inject.Singleton;

@Singleton
public class DynamoDBRepository implements DBRepository {

    @Override
    public boolean OtpValidation() {
        return true;
    }
}
