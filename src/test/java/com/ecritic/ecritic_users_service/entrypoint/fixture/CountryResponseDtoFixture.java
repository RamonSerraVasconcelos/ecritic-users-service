package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.CountryResponseDto;

public class CountryResponseDtoFixture {

    public static CountryResponseDto load() {
        return CountryResponseDto.builder()
                .id(19L)
                .name("Brazil")
                .build();
    }
}
