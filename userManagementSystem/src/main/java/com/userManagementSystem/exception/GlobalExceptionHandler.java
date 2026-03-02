package com.userManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError>error(ResourceNotFoundException e ){

        ApiError Build = ApiError.builder().localDateTime(LocalDateTime.now())
                .message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(Build,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError>err(BadCredentialsException e ){

        ApiError Build = ApiError.builder().localDateTime(LocalDateTime.now())
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(Build,HttpStatus.BAD_REQUEST);}
}
