package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.CountryEntityFixture;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CountryEntityMapperTest {

    @Test
    void givenCountryEntity_thenReturnCountry() {
        CountryEntityMapper countryEntityMapper = Mappers.getMapper(CountryEntityMapper.class);

        CountryEntity countryEntity = CountryEntityFixture.load();

        Country country = countryEntityMapper.countryEntityToCountry(countryEntity);

        assertThat(country).usingRecursiveComparison().isEqualTo(countryEntity);
    }
}