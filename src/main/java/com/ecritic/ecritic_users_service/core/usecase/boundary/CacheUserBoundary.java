package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.User;

public interface CacheUserBoundary {

    void execute(User user);
}
