package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

    AddressEntity addressToAddressEntity(Address address);

    Address addressEntityToAddress(AddressEntity addressEntity);
}
