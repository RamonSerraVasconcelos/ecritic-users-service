package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;
import com.ecritic.ecritic_users_service.exception.UnauthorizedAccessException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthorizationTokenDataMapperTest {

    @InjectMocks
    private AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private static final String AUTHORIZATION_TOKEN = "eyJhbGciOiJub25lIn0.eyJqdGkiOiI5M2IzMjIxOS0xYWU5LTQ0NTQtYTBhNC01ZjU2OTU2NGM2c2QiLCJ1c2VySWQiOiIwYzVkODM4OC03ZmU1LTRmNGUtYTNmMS05ZWFhMDgzZDJkNzkiLCJyb2xlIjoiREVGQVVMVCIsImlhdCI6OTkxODMzNjIzOSwiZXhwIjo5OTE4MzM2MjM5fQ.";
    private static final String AUTHORIZATION_TOKE_WITH_INVALID_ROLE = "eyJhbGciOiJub25lIn0.eyJqdGkiOiI5M2IzMjIxOS0xYWU5LTQ0NTQtYTBhNC01ZjU2OTU2NGM2c2QiLCJ1c2VySWQiOiIwYzVkODM4OC03ZmU1LTRmNGUtYTNmMS05ZWFhMDgzZDJkNzkiLCJpYXQiOjk5MTgzMzYyMzksImV4cCI6OTkxODMzNjIzOX0.";

    @Test
    void givenAuthorizationToken_thenExtractClaims_andReturnAuthorizationTokenData() {
        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(AUTHORIZATION_TOKEN);

        assertThat(authorizationTokenData).isNotNull();
        assertThat(authorizationTokenData.getUserId()).isEqualTo(UUID.fromString("0c5d8388-7fe5-4f4e-a3f1-9eaa083d2d79"));
        assertThat(authorizationTokenData.getUserRole()).isEqualTo(Role.DEFAULT);
    }

    @Test
    void givenAuthorizationTokenWithInvalidRole_thenThrowResourceViolationException() {
        UnauthorizedAccessException ex = assertThrows(UnauthorizedAccessException.class, () -> authorizationTokenDataMapper.map(AUTHORIZATION_TOKE_WITH_INVALID_ROLE));

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_11.getCode());
    }

    @Test
    void givenNullAuthorizationToken_thenThrowUnauthorizedAccessException() {
        UnauthorizedAccessException ex = assertThrows(UnauthorizedAccessException.class, () -> authorizationTokenDataMapper.map(null));

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_02.getCode());
    }
}