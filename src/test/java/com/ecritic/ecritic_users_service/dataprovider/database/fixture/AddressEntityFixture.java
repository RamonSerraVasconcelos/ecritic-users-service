package com.ecritic.ecritic_users_service.dataprovider.database.fixture;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddressEntityFixture {

    public static AddressEntity load() {
        return AddressEntity.builder()
                .id(UUID.randomUUID())
                .country(CountryEntityFixture.load())
                .uf("SP")
                .city("São Paulo")
                .neighborhood("Vila Oliímpia")
                .street("Avenida Brigadeiro Faria Lima")
                .postalCode("04538-000")
                .complement("Casa")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
