package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.usecase.boundary.UnsetDefaultUserAddressBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UnsetDefaultUserAddressGateway implements UnsetDefaultUserAddressBoundary {

    private final AddressEntityRepository addressEntityRepository;

    @Override
    public void execute(UUID userId) {
        addressEntityRepository.unsetUserDefaultAddress(userId);
    }
}
