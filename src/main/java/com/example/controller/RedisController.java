package com.example.controller;

import com.example.services.impl.RedisService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @Post("/save")
    public void createPoolClient(String value) throws ExecutionException, InterruptedException, IOException {
        System.out.println("hola");
        redisService.save(value);
    }

}
