package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUsersByParamsBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUsersByParamsGateway implements FindUsersByParamsBoundary {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public List<User> execute(UserFilter userFilter) {
        List<UserEntity> userEntityList = userEntityRepository.findUserListByParams(userFilter);

        return userEntityList.stream()
                .map(userEntityMapper::userEntityToUser)
                .toList();
    }
}
