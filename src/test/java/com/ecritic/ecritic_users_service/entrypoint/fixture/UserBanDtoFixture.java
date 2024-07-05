package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.UserBanDto;

public class UserBanDtoFixture {

    public static UserBanDto load() {
        return UserBanDto.builder()
                .action("BAN")
                .motive("Just test scenarios")
                .build();
    }
}
