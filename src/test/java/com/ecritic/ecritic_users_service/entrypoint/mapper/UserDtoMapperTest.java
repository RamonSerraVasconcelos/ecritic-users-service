package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserRequestDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoMapperTest {

    private UserDtoMapper userDtoMapper;

    @BeforeEach
    public void setUp() {
        userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
    }

    @Test
    void givenUserRequestDto_thenReturnUser() {
        UserRequestDto userRequestDto = UserRequestDtoFixture.load();

        User user = userDtoMapper.userRequestDtoToUser(userRequestDto);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(userRequestDto.getName());
        assertThat(user.getEmail()).isEqualTo(userRequestDto.getEmail());
        assertThat(user.getPassword()).isEqualTo(userRequestDto.getPassword());
        assertThat(user.getDescription()).isEqualTo(userRequestDto.getDescription());
        assertThat(user.getPhone()).isEqualTo(userRequestDto.getPhone());
        assertThat(user.getCountry().getId()).isEqualTo(userRequestDto.getCountryId());
    }

    @Test
    void givenUser_thenReturnUserResponseDto() {
        User user = UserFixture.load();

        UserResponseDto userResponseDto = userDtoMapper.userToUserResponseDto(user);

        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getId()).isEqualTo(user.getId().toString());
        assertThat(userResponseDto.getName()).isEqualTo(user.getName());
        assertThat(userResponseDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponseDto.getDescription()).isEqualTo(user.getDescription());
        assertThat(userResponseDto.getPhone()).isEqualTo(user.getPhone());
        assertThat(userResponseDto.getRole()).isEqualTo(user.getRole().name());
        assertThat(userResponseDto.getCountry().getId()).isEqualTo(user.getCountry().getId());
        assertThat(userResponseDto.getCountry().getName()).isEqualTo(user.getCountry().getName());
        assertThat(userResponseDto.getCreatedAt()).isEqualTo(user.getCreatedAt());
    }
}