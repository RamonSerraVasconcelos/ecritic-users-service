package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.CountryEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.CountryEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCountryByIdGateway implements FindCountryByIdBoundary {

    private final CountryEntityRepository countryEntityRepository;

    private final CountryEntityMapper countryEntityMapper;

    public Optional<Country> execute(Long countryId) {
        log.info("Finding country with id: [{}]", countryId);

        return countryEntityRepository
                .findById(countryId)
                .map(countryEntityMapper::countryEntityToCountry);
    }
}
