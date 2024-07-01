package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserByEmailUseCase {

    private final FindUserByEmailBoundary findUserByEmailBoundary;

    public User execute(String email) {
        log.info("Finding user by email: {}", email);

        try {
            User user = findUserByEmailBoundary.execute(email);

            if (isNull(user)) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
            }

            return user;
        } catch (Exception ex) {
            log.error("Error finding user by email: {}", email, ex);
            throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
        }
    }
}
