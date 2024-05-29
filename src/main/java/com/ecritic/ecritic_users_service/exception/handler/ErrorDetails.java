package com.ecritic.ecritic_users_service.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetails {

    private String message;
}
