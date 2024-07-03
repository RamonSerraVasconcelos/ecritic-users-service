package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.exception.ForbiddenAccessException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@NoArgsConstructor
public class ValidateUserRoleUseCase {

    public void execute(EnumSet<Role> allowedRoles, Role currentRole) {
        if (currentRole == null) {
            throw new ForbiddenAccessException(ErrorResponseCode.ECRITICUSERS_16);
        }

        if (currentRole == Role.ADMIN || allowedRoles.contains(currentRole)) {
            return;
        }

        throw new ForbiddenAccessException(ErrorResponseCode.ECRITICUSERS_16);
    }
}
