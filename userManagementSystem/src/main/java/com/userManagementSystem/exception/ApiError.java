package com.userManagementSystem.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@Builder
public class ApiError {

    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus httpStatus;
}
