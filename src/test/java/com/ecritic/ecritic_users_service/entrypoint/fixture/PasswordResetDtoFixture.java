package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.PasswordResetDto;

import java.util.UUID;

public class PasswordResetDtoFixture {

    public static PasswordResetDto load() {
        return PasswordResetDto.builder()
                .passwordResetHash(UUID.randomUUID().toString())
                .password("password")
                .passwordConfirmation("password")
                .build();
    }
}
