package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUserGateway implements SaveUserBoundary {

    private final UserEntityRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    public User execute(User user) {
        UserEntity userEntity = userEntityMapper.userToUserEntity(user);

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userEntityMapper.userEntityToUser(savedUserEntity);
    }
}
