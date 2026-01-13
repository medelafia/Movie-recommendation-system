package com.backend.exceptions;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsernameAlreadyTakenException extends RuntimeException {
    private String message;
}
