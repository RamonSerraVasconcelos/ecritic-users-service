package com.ecritic.ecritic_users_service.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorResponseCode {

    ECRITICUSERS_01("ECRITICUSERS-01", "Invalid request data", "Invalid request data"),
    ECRITICUSERS_02("ECRITICUSERS-02", "Missing required headers", "Missing required headers"),
    ECRITICUSERS_03("ECRITICUSERS-03", "Unauthorized access", "Unauthorized access"),
    ECRITICUSERS_04("ECRITICUSERS-04", "Resource not found", "Resource not found"),
    ECRITICUSERS_05("ECRITICUSERS-05", "Resource conflict", "Resource conflict"),
    ECRITICUSERS_06("ECRITICUSERS-06", "Resource violation", "Resource violation"),
    ECRITICUSERS_07("ECRITICUSERS-07", "Email conflict", "User email already exists"),
    ECRITICUSERS_08("ECRITICUSERS-08", "Country not found", "The requested country was not found"),
    ECRITICUSERS_09("ECRITICUSERS-09", "User not found", "The requested user was not found"),
    ECRITICUSERS_10("ECRITICUSERS-10", "Address not found", "The requested address was not found"),
    ECRITICUSERS_11("ECRITICUSERS-11", "Unauthorized", "Invalid authorization token");

    private final String code;
    private final String message;
    private final String detail;
}
