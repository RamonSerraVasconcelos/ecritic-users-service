package com.ecritic.ecritic_users_service.entrypoint.validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class RequestHeaders {

    private String requestId;
}
