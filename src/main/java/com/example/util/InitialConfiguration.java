package com.example.util;

import io.micronaut.context.annotation.ConfigurationInject;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("util.initial.config")
public class InitialConfiguration {

    @Getter
    private final String es;
    @Getter
    private final String en;

    @ConfigurationInject
    public InitialConfiguration(@NotBlank String es, @NotBlank String en) {
        this.es = es;
        this.en = en;
    }
}
