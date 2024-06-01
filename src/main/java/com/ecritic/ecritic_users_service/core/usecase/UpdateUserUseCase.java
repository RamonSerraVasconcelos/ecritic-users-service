package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUserCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserUseCase {

    private final SaveUserBoundary saveUserBoundary;

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final FindCountryByIdBoundary findCountryByIdBoundary;

    private final InvalidateUsersCacheBoundary invalidateUsersCacheBoundary;

    private final InvalidateUserCacheBoundary invalidateUserCacheBoundary;

    public User execute(UUID userId, User userRequest) {
        log.info("Updating user with id: [{}]", userRequest.getId());

        try {
            Optional<User> userToBeUpdatedOptional = findUserByIdBoundary.execute(userId);

            if(userToBeUpdatedOptional.isEmpty()) {
                throw new EntityNotFoundException("User not found");
            }

            Country country = findCountryByIdBoundary.execute(userRequest.getCountry().getId());

            User userToBeUpdated = userToBeUpdatedOptional.get();

            userToBeUpdated.setName(userRequest.getName());
            userToBeUpdated.setDescription(userRequest.getDescription());
            userToBeUpdated.setPhone(userRequest.getPhone());
            userToBeUpdated.setCountry(country);

            invalidateUsersCacheBoundary.execute();
            invalidateUserCacheBoundary.execute(userId);

            return saveUserBoundary.execute(userToBeUpdated);
        } catch (Exception ex) {
            log.error("Error while updating user with id: [{}]", userId, ex);
            throw ex;
        }
    }
}
