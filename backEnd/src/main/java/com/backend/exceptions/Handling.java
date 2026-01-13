package com.backend.exceptions;


import com.backend.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.Timestamp;
import java.time.Instant;

@RestControllerAdvice
public class Handling {

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    private ResponseEntity<ErrorDetails> handlingUsernameTakenException(UsernameAlreadyTakenException usernameAlreadyTakenException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .message(usernameAlreadyTakenException.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Timestamp.from(Instant.now()))
                        .build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    private ResponseEntity<ErrorDetails> handlingPasswordWrongException(InvalidPasswordException invalidPasswordException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .message(invalidPasswordException.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Timestamp.from(Instant.now()))
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorDetails> handlingPasswordWrongException(UserNotFoundException userNotFoundException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .message(userNotFoundException.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Timestamp.from(Instant.now()))
                        .build());
    }


}