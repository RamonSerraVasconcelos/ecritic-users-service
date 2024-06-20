package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.AddressResponseDto;

import java.util.UUID;

public class AddressResponseDtoFixture {

    public static AddressResponseDto load() {
        return AddressResponseDto.builder()
                .id(UUID.randomUUID())
                .country(CountryResponseDtoFixture.load())
                .uf("SP")
                .city("São Paulo")
                .neighborhood("Vila Oliímpia")
                .street("Avenida Brigadeiro Faria Lima")
                .postalCode("04538-000")
                .complement("Casa")
                .build();
    }
}
