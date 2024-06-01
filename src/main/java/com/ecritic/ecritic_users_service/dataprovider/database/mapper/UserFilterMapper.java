package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserFilterMapper {

    public abstract UserFilter map(Pageable pageable, boolean active, String name, String email, List<String> userIds);
}
