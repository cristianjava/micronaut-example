package com.example.services;

public interface ISNSNotificationService {

    public void smsNotification(String phoneNumber);
    public void emailNotification(String email);
}
