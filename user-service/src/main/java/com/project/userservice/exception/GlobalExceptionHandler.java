package com.project.userservice.exception;

import com.project.userservice.payload.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.getMessage(), request.getDescription(false), LocalDateTime.now()
        );
        return ResponseEntity.ok(exceptionResponse);
    }
}
