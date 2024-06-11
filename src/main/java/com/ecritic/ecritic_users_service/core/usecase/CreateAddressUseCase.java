package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class CreateAddressUseCase extends AbstractAddressUseCase {

    CreateAddressUseCase(FindCountryByIdBoundary findCountryByIdBoundary, SaveAddressBoundary saveAddressBoundary) {
        super(findCountryByIdBoundary, saveAddressBoundary);
    }

    public Address execute(Address address) {
        log.info("Creating address");

        validateCountry(address.getCountry().getId());

        address.setId(UUID.randomUUID());
        address.setCreatedAt(LocalDateTime.now());

        return upsertAddress(address);
    }
}
