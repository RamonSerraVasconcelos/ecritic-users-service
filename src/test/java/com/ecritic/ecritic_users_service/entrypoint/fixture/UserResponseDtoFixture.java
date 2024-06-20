package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponseDtoFixture {

    public static UserResponseDto load() {
        return UserResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .name("Ryan Gosling")
                .email("ryangosling@email.com")
                .description("I drive")
                .role("DEFAULT")
                .country(CountryResponseDtoFixture.load())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
