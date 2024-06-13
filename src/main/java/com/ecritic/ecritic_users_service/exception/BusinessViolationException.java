package com.ecritic.ecritic_users_service.exception;

import com.ecritic.ecritic_users_service.exception.handler.ErrorResponse;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;

public class BusinessViolationException extends DefaultException {

    public BusinessViolationException(ErrorResponseCode errorResponseCode) {
        super(ErrorResponse.builder()
                .code(errorResponseCode.getCode())
                .message(errorResponseCode.getMessage())
                .detail(errorResponseCode.getDetail())
                .build());
    }
}
