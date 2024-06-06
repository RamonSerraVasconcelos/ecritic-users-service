package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAddressGateway implements SaveAddressBoundary {

    private final AddressEntityRepository addressEntityRepository;

    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Address execute(Address address) {
        AddressEntity addressEntity = addressEntityMapper.addressToAddressEntity(address);

        AddressEntity savedEntity = addressEntityRepository.save(addressEntity);

        return addressEntityMapper.addressEntityToAddress(savedEntity);
    }
}
