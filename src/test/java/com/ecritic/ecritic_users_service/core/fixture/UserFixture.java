package com.ecritic.ecritic_users_service.core.fixture;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.core.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserFixture {

    public static User load() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("Ryan Gosling")
                .email("ryangosling@email.com")
                .password("bladerunner2049")
                .description("I drive")
                .phone("5548999999999")
                .country(CountryFixture.load())
                .role(Role.DEFAULT)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
