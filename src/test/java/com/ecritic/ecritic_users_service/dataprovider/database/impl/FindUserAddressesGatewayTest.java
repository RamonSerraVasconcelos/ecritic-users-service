package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.AddressEntityFixture;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserAddressesGatewayTest {

    @InjectMocks
    private FindUserAddressesGateway findUserAddressesGateway;

    @Mock
    private AddressEntityRepository addressEntityRepository;

    @Mock
    private AddressEntityMapper addressEntityMapper;

    @Test
    void givenExecution_callAddressRepository_thenReturnAddress() {
        Address address = AddressFixture.load();
        AddressEntity addressEntity = AddressEntityFixture.load();
        UUID userId = UUID.randomUUID();

        when(addressEntityRepository.findUserAddressesByUserId(any())).thenReturn(List.of(addressEntity, addressEntity));
        when(addressEntityMapper.addressEntityToAddress(any())).thenReturn(address, address);

        List<Address> foundAddresses = findUserAddressesGateway.execute(userId);

        verify(addressEntityRepository).findUserAddressesByUserId(any());

        assertThat(foundAddresses).isNotEmpty().hasSize(2).usingRecursiveComparison().isEqualTo(List.of(address, address));
    }
}