package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.UserFilter;

public interface CountUsersBoundary {

    Long execute(UserFilter userFilter);
}
