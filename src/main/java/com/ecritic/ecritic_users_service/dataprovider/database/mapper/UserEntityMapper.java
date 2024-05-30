package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);
}
