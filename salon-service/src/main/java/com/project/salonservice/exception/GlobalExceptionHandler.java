package com.project.salonservice.exception;

import com.project.salonservice.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(SalonNotFoundException.class)
    public ResponseEntity<String> handleSalonNotFound(SalonNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
}
