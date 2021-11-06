package com.example.vo;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;

@Getter
@Introspected
public class Event {
    private final String id;
    private final String body;

    public Event(String id, String body) {
        this.id = id;
        this.body = body;
    }
}
