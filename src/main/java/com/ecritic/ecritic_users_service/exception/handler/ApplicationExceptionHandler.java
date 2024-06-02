package com.ecritic.ecritic_users_service.exception.handler;

import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import com.ecritic.ecritic_users_service.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

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

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorDetails> unauthorizedAccessExceptionHandler(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildResponseError(ex.getMessage()));
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorDetails> fileSizeLimitExceededExceptionHandler(FileSizeLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError("File size limit exceeded"));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorDetails> multipartExceptionHandler(MultipartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.warn("Request INFO - Response returning violations found: [{}]", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError("Invalid request data"));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("Request INFO - Response returning violations found: [{}]", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseError("Invalid request data"));
    }
}
