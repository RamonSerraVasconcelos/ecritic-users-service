package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCache;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCase {

    private final SaveUserBoundary saveUserBoundary;

    private final FindUserByEmailBoundary findUserByEmailBoundary;

    private final FindCountryByIdBoundary findCountryByIdBoundary;

    private final InvalidateUsersCache invalidateUsersCache;

    private final BCryptPasswordEncoder bcrypt;

    public User execute(User user) {
        log.info("Creating user with name: [{}]", user.getName());

        try {
            User isUserDuplicated = findUserByEmailBoundary.execute(user.getEmail());

            if (nonNull(isUserDuplicated)) {
                throw new EntityConflictException("User email already exists");
            }

            Country country = findCountryByIdBoundary.execute(user.getCountry().getId());
            String encodedPassword = bcrypt.encode(user.getPassword());

            user.setId(UUID.randomUUID());
            user.setCountry(country);
            user.setPassword(encodedPassword);
            user.setActive(true);
            user.setRole(Role.DEFAULT);

            invalidateUsersCache.execute();

            return saveUserBoundary.execute(user);
        } catch (Exception ex) {
            log.error("Error creating user", ex);
            throw ex;
        }
    }
}
