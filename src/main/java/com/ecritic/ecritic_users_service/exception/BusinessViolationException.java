package com.ecritic.ecritic_users_service.exception;

public class BusinessViolationException extends RuntimeException {

    public BusinessViolationException(String message) {
        super(message);
    }
}
