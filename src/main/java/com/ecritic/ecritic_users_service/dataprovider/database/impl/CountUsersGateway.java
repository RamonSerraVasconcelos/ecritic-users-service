package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CountUsersBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountUsersGateway implements CountUsersBoundary {

    private final UserEntityRepository userRepository;

    public Long execute(UserFilter userFilter) {
        return userRepository.countUsersByParams(userFilter);
    }
}
