package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressesBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindUserAddressesGateway implements FindUserAddressesBoundary {

    private final AddressEntityRepository addressEntityRepository;

    private final AddressEntityMapper addressEntityMapper;

    public List<Address> execute(UUID userId) {
        List<AddressEntity> addressEntities = addressEntityRepository.findUserAddressesByUserId(userId);
        
        return addressEntities.stream()
                .map(addressEntityMapper::addressEntityToAddress)
                .toList();
    }
}
