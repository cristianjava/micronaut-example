package com.example.services.impl;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Singleton
public class AuthenticationUserPass implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationUserPass.class);
    /**
     * Authenticates a user with the given request. If a successful authentication is
     * returned, the object must be an instance of {@link UserDetails}.
     * <p>
     * Publishers <b>MUST emit cold observables</b>! This method will be called for
     * all authenticators for each authentication request and it is assumed no work
     * will be done until the publisher is subscribed to.
     *
     * @param httpRequest           The http request
     * @param authenticationRequest The credentials to authenticate
     * @return A publisher that emits 0 or 1 responses
     */
    @Override
    public Publisher<AuthenticationResponse> authenticate(
            @Nullable final HttpRequest<?> httpRequest,
            final AuthenticationRequest<?, ?> authenticationRequest) {
        final HashMap<String, Object> customAttributes = new HashMap<>();
        customAttributes.put("phone", "3217777777");
        customAttributes.put("Address", "Marinilla Antioquia");
        Publisher<AuthenticationResponse> result =  Flowable.create(emitter -> {
            final Object identity = authenticationRequest.getIdentity();
            final Object secret = authenticationRequest.getSecret();
            LOG.info("User {} tries to login...",identity);

            if (identity.equals("user") && secret.equals("pass")) {
                emitter.onNext(new UserDetails((String) identity,
                        Arrays.asList("ROLE_USER", "ROLE_ADMIN"),
                        customAttributes));
                emitter.onComplete();
                return;
            }
            emitter.onError(new AuthenticationException(new AuthenticationFailed("Wrong username or password")));
        }, BackpressureStrategy.ERROR);
        return result;
    }
}
