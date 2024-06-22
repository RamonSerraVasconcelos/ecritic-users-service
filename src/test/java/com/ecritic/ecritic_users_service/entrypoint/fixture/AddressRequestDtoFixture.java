package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.AddressRequestDto;

public class AddressRequestDtoFixture {

    public static AddressRequestDto load() {
        return AddressRequestDto.builder()
                .countryId(1L)
                .uf("SP")
                .city("São Paulo")
                .neighborhood("Vila Oliímpia")
                .street("Avenida Brigadeiro Faria Lima")
                .postalCode("04538-000")
                .complement("Casa")
                .build();
    }
}
