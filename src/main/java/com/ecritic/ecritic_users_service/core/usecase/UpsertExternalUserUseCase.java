package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.InternalErrorException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpsertExternalUserUseCase {

    private final FindUserByEmailBoundary findUserByEmailBoundary;

    private final SaveUserBoundary saveUserBoundary;

    public User execute(String email, String name) {
        log.info("Upserting user with email: [{}]", email);

        try {
            User user = findUserByEmailBoundary.execute(email);

            if (nonNull(user)) {
                log.info("Returning found user with id [{}]", user.getId());
                return user;
            }

            User newUser = User.builder()
                    .id(UUID.randomUUID())
                    .email(email)
                    .name(name)
                    .role(Role.DEFAULT)
                    .active(true)
                    .build();

            log.info("Saving new user with id [{}]", newUser.getId());

            return saveUserBoundary.execute(newUser);
        } catch (DefaultException ex) {
            log.error("Error upserting user with email: [{}]. Error: [{}]", email, ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error upserting user with email: [{}]", email, ex);
            throw new InternalErrorException(ErrorResponseCode.ECRITICUSERS_17);
        }
    }
}
