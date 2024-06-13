package com.ecritic.ecritic_users_service.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private String detail;
}
