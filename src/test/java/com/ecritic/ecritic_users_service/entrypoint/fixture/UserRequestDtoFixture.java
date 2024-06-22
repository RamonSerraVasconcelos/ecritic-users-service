package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;

public class UserRequestDtoFixture {

    public static UserRequestDto load() {
        return UserRequestDto.builder()
                .name("Ryan Gosling")
                .email("ryangosling@email.com")
                .password("12345678")
                .passwordConfirmation("12345678")
                .description("I drive")
                .countryId(219L)
                .build();
    }
}
