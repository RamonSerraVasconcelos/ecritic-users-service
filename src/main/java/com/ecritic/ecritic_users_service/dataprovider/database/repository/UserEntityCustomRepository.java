package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;

import java.util.List;

public interface UserEntityCustomRepository {

    List<UserEntity> findUserListByParams(UserFilter userFilter);

    Long countUsersByParams(UserFilter userFilter);
}
