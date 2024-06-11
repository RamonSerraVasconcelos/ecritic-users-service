package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Country;

import java.util.Optional;

public interface FindCountryByIdBoundary {

    Optional<Country> execute(Long id);
}
