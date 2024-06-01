package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.User;
import org.springframework.data.domain.Page;

public interface CacheUsersBoundary {

    void execute(Page<User> users);
}
