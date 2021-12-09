package com.example.controller;

import com.example.services.HelloWorldService;
import com.example.util.InitialConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
public class HelloWorldController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    private final HelloWorldService helloWorldService;
    private final InitialConfiguration initialConfiguration;

    public HelloWorldController(final HelloWorldService helloWorldService, InitialConfiguration initialConfiguration) {
        this.helloWorldService = helloWorldService;
        this.initialConfiguration = initialConfiguration;
    }

    @Get("${controller.hello.path}")
    public String hello() {
        return helloWorldService.hello();
    }

    @Get("/es")
    public String SpanishConfig() {
        return initialConfiguration.getEs();
    }

    @Get("/en")
    public String EnglishConfig() {
        return initialConfiguration.getEn();
    }
}
