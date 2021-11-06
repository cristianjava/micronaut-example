package com.example.controller;

import com.example.services.impl.RedisService;
import io.micronaut.http.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @Post("/save")
    public void createPoolClient(String value) throws ExecutionException, InterruptedException, IOException {
        redisService.save(value);
    }

}