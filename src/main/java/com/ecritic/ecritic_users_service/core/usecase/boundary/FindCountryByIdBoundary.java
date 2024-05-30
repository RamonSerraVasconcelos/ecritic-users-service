package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Country;

import java.util.UUID;

public interface FindCountryByIdBoundary {

    Country execute(UUID id);
}
