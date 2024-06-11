package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    AddressEntity addressToAddressEntity(Address address);

    @Mapping(target = "isDefault", source = "default")
    Address addressEntityToAddress(AddressEntity addressEntity);
}
