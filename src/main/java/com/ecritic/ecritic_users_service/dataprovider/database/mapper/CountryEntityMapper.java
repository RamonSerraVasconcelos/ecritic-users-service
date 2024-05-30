package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryEntityMapper {

    Country countryEntityToCountry(CountryEntity countryEntity);
}
