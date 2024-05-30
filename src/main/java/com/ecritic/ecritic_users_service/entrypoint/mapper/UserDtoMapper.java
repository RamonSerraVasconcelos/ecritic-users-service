package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserDtoMapper {

    @Mapping(target = "country.id", source = "countryId")
    public abstract User userRequestDtoToUser(UserRequestDto userRequestDto);

    public abstract UserResponseDto userToUserResponseDto(User user);
}
