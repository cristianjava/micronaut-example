package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InitiateAuthResponseDTO {

    private String accessToken;
    private String refreshToken;
    private Integer sessionExpirationIn;

}
