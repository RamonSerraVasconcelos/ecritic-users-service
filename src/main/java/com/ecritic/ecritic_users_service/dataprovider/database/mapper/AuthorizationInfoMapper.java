package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorizationInfoMapper {

    AuthorizationInfo userToAuthorizationInfo(User user);
}
