package com.example.controller;

import com.example.services.impl.CognitoService;
import com.example.vo.InitiateAuthResponseDTO;
import com.example.vo.LoginData;
import com.example.vo.UserModel;
import io.micronaut.http.annotation.*;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Controller("/cognito")
public class CognitoController {

    private CognitoService cognitoService;

    public CognitoController(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    @Get("/getUserPools")
    public String getUserPoolUsers() {
        return cognitoService.getUserPoolUsers();
    }

    @Post("/createPoolClient")
    public String createPoolClient(String clientName, String userPoolId) {
        return cognitoService.createPoolClient(clientName, userPoolId);
    }

    @Post("/createNewUser")
    public void createNewUser(String userPoolId, String name, String email, String password) {
        cognitoService.createNewUser(userPoolId, name, email, password);
    }

    @Post("/forgotPassword")
    public String forgotPassword(String clientId, String clientSecret, String userName) {
        return cognitoService.forgotPassword(clientId, clientSecret, userName);
    }

    @Post("/changePassword")
    public String changePassword(String accessToken, String previousPass, String proposedPass) {
        return cognitoService.changePassword(accessToken, previousPass, proposedPass);
    }

    @Post("/login")
    public String login(String username, String password) {
        return cognitoService.initAuth(username, password);
    }

    @Post("/welcomeNotify")
    public String welcomeNotify() {
        return cognitoService.welcomeNotify();
    }

    @Post("/signUp")
    public String signUp(String clientId, String clientSecret, String userName, String password) {
        return cognitoService.signUp(clientId, clientSecret, password, userName);
    }

    @Post("/confirmSignUp")
    public String confirmSignUp(String clientId, String clientSecret, String confirmationCode, String userName) {
        return cognitoService.confirmSignUp(clientId, clientSecret, confirmationCode, userName);
    }

    @Post("/init-auth")
    InitiateAuthResponseDTO login(@Body LoginData loginData) {
        return cognitoService.login(loginData);
    }

    @Get("/getInfoUser")
    UserModel getInfoUser(@Header(AUTHORIZATION) String authorization) {
        return cognitoService.getInfoUser(authorization);
    }

}
