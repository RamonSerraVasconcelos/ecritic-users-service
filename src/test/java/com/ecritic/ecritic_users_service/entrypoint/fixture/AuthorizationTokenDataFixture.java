package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;

import java.util.UUID;

public class AuthorizationTokenDataFixture {

    public static AuthorizationTokenData load() {
        return AuthorizationTokenData.builder()
                .userId(UUID.randomUUID())
                .userRole(Role.DEFAULT)
                .build();
    }
}
