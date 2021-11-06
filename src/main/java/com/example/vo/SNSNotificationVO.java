package com.example.vo;

import lombok.Getter;

import java.io.Serializable;

public class SNSNotificationVO implements Serializable {

    @Getter
    private final String phoneNumber;
    @Getter
    private final String email;

    public SNSNotificationVO(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
