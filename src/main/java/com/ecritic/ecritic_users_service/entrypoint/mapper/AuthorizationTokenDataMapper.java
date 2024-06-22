package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;
import com.ecritic.ecritic_users_service.entrypoint.validation.TokenUtils;
import com.ecritic.ecritic_users_service.exception.UnauthorizedAccessException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class AuthorizationTokenDataMapper {

    public AuthorizationTokenData map(String authorizationToken) {
        if (isNull(authorizationToken)) {
            throw new UnauthorizedAccessException(ErrorResponseCode.ECRITICUSERS_02, "Missing authorization token");
        }

        try {
            UUID userId = UUID.fromString(TokenUtils.getUserIdFromToken(authorizationToken));
            String userRole = TokenUtils.getUserRoleFromToken(authorizationToken);

            return AuthorizationTokenData.builder()
                    .userId(userId)
                    .userRole(Role.parseRole(userRole))
                    .build();
        } catch (Exception ex) {
            log.error("Error parsing access token", ex);
            throw new UnauthorizedAccessException(ErrorResponseCode.ECRITICUSERS_11);
        }
    }
}
