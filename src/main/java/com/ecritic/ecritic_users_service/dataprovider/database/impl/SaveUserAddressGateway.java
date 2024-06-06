package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserAddressBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaveUserAddressGateway implements SaveUserAddressBoundary {

    private final AddressEntityRepository addressEntityRepository;

    @Override
    public void execute(UUID userId, UUID address) {
        addressEntityRepository.saveUserAddress(userId, address);
    }
}
