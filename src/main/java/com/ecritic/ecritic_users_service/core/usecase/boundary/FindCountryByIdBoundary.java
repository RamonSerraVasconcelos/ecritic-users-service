package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Country;

public interface FindCountryByIdBoundary {

    Country execute(Long id);
}
