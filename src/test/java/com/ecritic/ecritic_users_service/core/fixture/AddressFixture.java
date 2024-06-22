package com.ecritic.ecritic_users_service.core.fixture;

import com.ecritic.ecritic_users_service.core.model.Address;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddressFixture {

    public static Address load() {
        return Address.builder()
                .id(UUID.randomUUID())
                .country(CountryFixture.load())
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
