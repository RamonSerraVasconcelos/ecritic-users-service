package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.User;

public interface SaveUserBoundary {

    User execute(User user);
}
