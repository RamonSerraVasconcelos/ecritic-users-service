package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.fixture.CountryFixture;
import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.CountryEntityFixture;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.CountryEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.CountryEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCountryByIdGatewayTest {

    @InjectMocks
    private FindCountryByIdGateway findCountryByIdGateway;

    @Mock
    private CountryEntityRepository countryEntityRepository;

    @Mock
    private CountryEntityMapper countryEntityMapper;

    @Test
    void givenExecution_callCountryRepository_thenReturnCountry() {
        Country country = CountryFixture.load();
        CountryEntity countryEntity = CountryEntityFixture.load();

        when(countryEntityRepository.findById(any())).thenReturn(Optional.of(countryEntity));
        when(countryEntityMapper.countryEntityToCountry(any())).thenReturn(country);

        Optional<Country> foundCountry = findCountryByIdGateway.execute(country.getId());

        verify(countryEntityRepository).findById(any());

        assertThat(foundCountry).isPresent().get().usingRecursiveComparison().isEqualTo(country);
    }
}