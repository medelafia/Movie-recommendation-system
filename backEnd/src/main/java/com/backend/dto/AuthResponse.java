package com.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
