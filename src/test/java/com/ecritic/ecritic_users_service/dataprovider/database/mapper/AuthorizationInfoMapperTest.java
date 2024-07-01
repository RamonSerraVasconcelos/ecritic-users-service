package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorizationInfoMapperTest {

    private AuthorizationInfoMapper authorizationInfoMapper;

    @BeforeEach
    public void setUp() {
        authorizationInfoMapper = Mappers.getMapper(AuthorizationInfoMapper.class);
    }

    @Test
    void givenUser_thenReturnAuthorizationInfo() {
        User user = UserFixture.load();

        AuthorizationInfo authorizationInfo = authorizationInfoMapper.userToAuthorizationInfo(user);

        assertThat(authorizationInfo.getId()).isEqualTo(user.getId().toString());
        assertThat(authorizationInfo.getEmail()).isEqualTo(user.getEmail());
        assertThat(authorizationInfo.getPassword()).isEqualTo(user.getPassword());
        assertThat(authorizationInfo.getRole()).isEqualTo(user.getRole().name());
        assertThat(authorizationInfo.isActive()).isEqualTo(user.isActive());
    }
}