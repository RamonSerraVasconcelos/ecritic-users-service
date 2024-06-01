package com.ecritic.ecritic_users_service.exception.handler;

import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    private ErrorDetails buildResponseError(String message) {
        return ErrorDetails.builder()
                .message(message)
                .build();
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorDetails> resourceConflictExceptionHandler(EntityConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildResponseError(ex.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponseError(ex.getMessage()));
    }

    @ExceptionHandler(BusinessViolationException.class)
    public ResponseEntity<ErrorDetails> businessViolationExceptionHandler(BusinessViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError(ex.getMessage()));
    }

    @ExceptionHandler(ResourceViolationException.class)
    public ResponseEntity<ErrorDetails> resourceViolationExceptionHandler(ResourceViolationException ex) {
        log.warn("Request INFO - Response returning violations found: [{}]", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError(ex.getMessage()));
    }
}
