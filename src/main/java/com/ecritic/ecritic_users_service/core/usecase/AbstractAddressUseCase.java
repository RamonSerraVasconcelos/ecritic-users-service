package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractAddressUseCase {

    private final FindCountryByIdBoundary findCountryByIdBoundary;

    private final SaveAddressBoundary saveAddressBoundary;

    protected void validateCountry(Long countryId) {
        Optional<Country> optionalCountry = findCountryByIdBoundary.execute(countryId);

        if (optionalCountry.isEmpty()) {
            throw new EntityNotFoundException("Invalid country");
        }
    }

    protected Address upsertAddress(Address address) {
        return saveAddressBoundary.execute(address);
    }
}
