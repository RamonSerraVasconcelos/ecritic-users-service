package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserEntityCustomRepository {

    List<UserEntity> findUserListByParams(UserFilter userFilter);

    Long countUsersByParams(UserFilter userFilter);

    void updateStatus(UUID id, boolean active);
}
