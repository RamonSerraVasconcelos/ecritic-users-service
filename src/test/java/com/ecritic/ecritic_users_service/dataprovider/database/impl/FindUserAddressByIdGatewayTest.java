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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserAddressByIdGatewayTest {

    @InjectMocks
    private FindUserAddressByIdGateway findUserAddressByIdGateway;

    @Mock
    private AddressEntityRepository addressEntityRepository;

    @Mock
    private AddressEntityMapper addressEntityMapper;

    @Test
    void givenExecution_thenFind_andReturnAddress() {
        Address address = AddressFixture.load();
        AddressEntity addressEntity = AddressEntityFixture.load();
        UUID userId = UUID.randomUUID();

        when(addressEntityRepository.findUserAddressByIds(any(), any())).thenReturn(addressEntity);
        when(addressEntityMapper.addressEntityToAddress(any())).thenReturn(address);

        Optional<Address> foundAddress = findUserAddressByIdGateway.execute(userId, address.getId());

        verify(addressEntityRepository).findUserAddressByIds(userId, address.getId());

        assertThat(foundAddress).isPresent().get().usingRecursiveComparison().isEqualTo(address);
    }
}