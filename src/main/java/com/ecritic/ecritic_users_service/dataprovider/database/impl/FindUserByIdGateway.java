package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindUserByIdGateway implements FindUserByIdBoundary {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityMapper userEntityMapper;

    public Optional<User> execute(UUID id) {
        return userEntityRepository
                .findById(id)
                .map(userEntityMapper::userEntityToUser);
    }
}
