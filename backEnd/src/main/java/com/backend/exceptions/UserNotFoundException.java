package com.backend.exceptions;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private String message;
}
