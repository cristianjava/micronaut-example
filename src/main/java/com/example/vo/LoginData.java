package com.example.vo;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {

    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    
}
