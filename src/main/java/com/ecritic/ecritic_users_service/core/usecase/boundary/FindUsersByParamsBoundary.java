package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;

import java.util.List;

public interface FindUsersByParamsBoundary {

    List<User> execute(UserFilter userFilter);
}
