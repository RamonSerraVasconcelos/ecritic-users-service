package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.usecase.boundary.SetDefaultUserAddressBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SetDefaultUserAddressGateway implements SetDefaultUserAddressBoundary {

    private final AddressEntityRepository addressEntityRepository;

    @Override
    public void execute(UUID userId, UUID addressId) {
        addressEntityRepository.setUserDefaultAddress(userId, addressId);
    }
}
