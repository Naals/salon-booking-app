package com.project.salonservice.exception;

public class SalonNotFoundException extends RuntimeException {
    public SalonNotFoundException(String message) {
        super(message);
    }
}

