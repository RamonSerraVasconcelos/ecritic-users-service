package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.exception.ForbiddenAccessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidateUserRoleUseCaseTest {

    @InjectMocks
    private ValidateUserRoleUseCase validateUserRoleUseCase;

    @Test
    void givenValidParameters_andMatchingRoles_thenAllowed() {
        EnumSet<Role> allowedRoles = EnumSet.of(Role.MODERATOR);

        assertDoesNotThrow(() -> validateUserRoleUseCase.execute(allowedRoles, Role.MODERATOR));
    }

    @Test
    void givenCallWithCurrentRoleAdmin_thenAlwaysAllow() {
        EnumSet<Role> allowedRoles = EnumSet.of(Role.MODERATOR);

        assertDoesNotThrow(() -> validateUserRoleUseCase.execute(allowedRoles, Role.ADMIN));
    }

    @Test
    void givenNullCurrentRole_thenThrowForbiddenAccessException() {
        EnumSet<Role> allowedRoles = EnumSet.of(Role.MODERATOR);

        assertThrows(ForbiddenAccessException.class, () -> validateUserRoleUseCase.execute(allowedRoles, null));
    }

    @Test
    void givenCallWithUnmatchedRole_thenThrowForbiddenAccessException() {
        EnumSet<Role> allowedRoles = EnumSet.of(Role.MODERATOR);

        assertThrows(ForbiddenAccessException.class, () -> validateUserRoleUseCase.execute(allowedRoles, Role.DEFAULT));
    }
}