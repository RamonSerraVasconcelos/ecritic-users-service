package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressDtoMapper {

    @Mapping(target = "country.id", source = "countryId")
    @Mapping(target = "isDefault", source = "default")
    Address addressRequestDtoToAddress(AddressRequestDto addressRequestDto);

    @Mapping(target = "isDefault", source = "default")
    AddressResponseDto addressToAddressResponseDto(Address address);
}
