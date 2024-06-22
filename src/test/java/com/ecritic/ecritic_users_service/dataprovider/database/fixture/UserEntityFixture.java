package com.ecritic.ecritic_users_service.dataprovider.database.fixture;

import com.ecritic.ecritic_users_service.core.fixture.CountryFixture;
import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.RoleEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserEntityFixture {

    public static UserEntity load() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .name("Ryan Gosling")
                .email("ryangosling@email.com")
                .password("bladerunner2049")
                .description("I drive")
                .phone("5548999999999")
                .country(CountryEntityFixture.load())
                .role(RoleEntity.DEFAULT)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
