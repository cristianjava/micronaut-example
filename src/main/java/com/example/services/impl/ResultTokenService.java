package com.example.services.impl;

import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;

import javax.inject.Singleton;

@Singleton
public class ResultTokenService {

    public BearerAccessRefreshToken micronautLogin(final String user, final String pass) {
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "pass");

        return null;

    }

}
