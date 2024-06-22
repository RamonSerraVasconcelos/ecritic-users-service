package com.ecritic.ecritic_users_service.entrypoint.dto;

import com.ecritic.ecritic_users_service.core.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorizationTokenData {

    private UUID userId;
    private Role userRole;
}
