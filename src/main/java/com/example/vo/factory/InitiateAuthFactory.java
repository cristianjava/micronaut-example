package com.example.vo.factory;

import com.example.vo.LoginData;
import com.example.vo.LoginModel;

import javax.inject.Singleton;

@Singleton
public class InitiateAuthFactory {

    public LoginModel create(LoginData loginData) {
        return new LoginModel(
            loginData.getUsername(),
            loginData.getPassword()
        );
    }

}
