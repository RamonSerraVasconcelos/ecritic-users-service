package com.ecritic.ecritic_users_service.core.usecase.boundary;


import com.ecritic.ecritic_users_service.core.model.User;

import java.util.Optional;
import java.util.UUID;

public interface FindUserByIdBoundary {

    Optional<User> execute(UUID id);
}
