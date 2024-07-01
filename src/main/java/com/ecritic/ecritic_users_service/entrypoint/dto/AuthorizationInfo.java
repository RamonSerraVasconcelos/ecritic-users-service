package com.ecritic.ecritic_users_service.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorizationInfo {

    private String id;
    private String email;
    private String password;
    private String role;
    private boolean active;
}
