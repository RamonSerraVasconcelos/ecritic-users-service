package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.Country;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAddressUseCase {

    private final FindCountryByIdBoundary findCountryByIdBoundary;

    private final SaveAddressBoundary saveAddressBoundary;

    public Address execute(Address address) {
        log.info("Creating address");

        Optional<Country> optionalCountry = findCountryByIdBoundary.execute(address.getCountry().getId());

        if (optionalCountry.isEmpty()) {
            throw new EntityNotFoundException("Invalid country");
        }

        address.setId(UUID.randomUUID());
        address.setCreatedAt(LocalDateTime.now());

        saveAddressBoundary.execute(address);

        return address;
    }
}
