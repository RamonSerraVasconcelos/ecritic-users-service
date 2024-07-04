package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUserBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUserBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserByIdUseCase {

    private final FindCachedUserBoundary findCachedUserBoundary;

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final CacheUserBoundary cacheUserBoundary;

    public User execute(UUID userId) {
        log.info("Finding user by id: [{}]", userId);

        try {

            Optional<User> optionaCachedlUser = findCachedUserBoundary.execute(userId);

            if (optionaCachedlUser.isPresent()) {
                log.info("Returning user from cache");
                return optionaCachedlUser.get();
            }

            Optional<User> optionalUser = findUserByIdBoundary.execute(userId);

            if (optionalUser.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
            }

            log.info("User not found in cache. Returning from database");
            User user = optionalUser.get();

            cacheUserBoundary.execute(user);

            return user;
        } catch (DefaultException ex) {
            log.error("Error finding user by id. Exception: [{}]", ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error finding user by id", ex);
            throw ex;
        }
    }
}
