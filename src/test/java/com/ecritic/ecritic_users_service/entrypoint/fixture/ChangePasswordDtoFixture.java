package com.ecritic.ecritic_users_service.entrypoint.fixture;

import com.ecritic.ecritic_users_service.entrypoint.dto.ChangePasswordDto;

public class ChangePasswordDtoFixture {

    public static ChangePasswordDto load() {
        return ChangePasswordDto.builder()
                .currentPassword("oldPassword")
                .newPassword("newPassword")
                .passwordConfirmation("newPassword")
                .build();
    }
}
