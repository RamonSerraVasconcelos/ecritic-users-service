package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindUserByEmailGateway implements FindUserByEmailBoundary {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityMapper userEntityMapper;

    public User execute(String email) {
        log.info("Finding user with email: [{}]", email);

        UserEntity userEntity = userEntityRepository.findByEmail(email);

        return userEntityMapper.userEntityToUser(userEntity);
    }
}
