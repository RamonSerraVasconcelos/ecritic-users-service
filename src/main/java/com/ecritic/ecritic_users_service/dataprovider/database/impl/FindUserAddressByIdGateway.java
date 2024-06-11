package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressByIdBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindUserAddressByIdGateway implements FindUserAddressByIdBoundary {

    private final AddressEntityRepository addressEntityRepository;

    private final AddressEntityMapper addressEntityMapper;

    public Optional<Address> execute(UUID userId, UUID addressId) {
        AddressEntity addressEntity = addressEntityRepository.findUserAddressByIds(userId, addressId);

        return Optional.ofNullable(addressEntityMapper.addressEntityToAddress(addressEntity));
    }
}
