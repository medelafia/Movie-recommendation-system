package com.backend.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String message;
    private Timestamp timestamp;
    private HttpStatus status;
}
