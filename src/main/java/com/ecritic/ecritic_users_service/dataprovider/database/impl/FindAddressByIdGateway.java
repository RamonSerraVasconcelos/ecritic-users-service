package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindAddressByIdBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindAddressByIdGateway implements FindAddressByIdBoundary {

    private final AddressEntityRepository addressEntityRepository;

    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Optional<Address> execute(UUID addressId) {
        return addressEntityRepository
                .findById(addressId)
                .map(addressEntityMapper::addressEntityToAddress);
    }
}
