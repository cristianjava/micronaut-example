package com.example.controller;

import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.security.Principal;
import java.util.Map;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/Auth")
public class AuthorizationController {

    @Get("/infoAuth")
    public Map<String, Object> getUserPoolUsers(Principal principal) {
        System.out.println("OK " + principal.getName() + " - " + ((Authentication) principal).getAttributes());
        return ((Authentication) principal).getAttributes();
    }

}
