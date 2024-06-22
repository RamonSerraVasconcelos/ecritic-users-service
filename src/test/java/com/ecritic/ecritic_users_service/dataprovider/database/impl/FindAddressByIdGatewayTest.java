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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAddressByIdGatewayTest {

    @InjectMocks
    private FindAddressByIdGateway findAddressByIdGateway;

    @Mock
    private AddressEntityRepository addressEntityRepository;

    @Mock
    private AddressEntityMapper addressEntityMapper;

    @Test
    void givenParams_thenFindAndReturnAddress() {
        Address address = AddressFixture.load();
        AddressEntity addressEntity = AddressEntityFixture.load();

        when(addressEntityRepository.findById(address.getId())).thenReturn(Optional.of(addressEntity));
        when(addressEntityMapper.addressEntityToAddress(addressEntity)).thenReturn(address);

        Optional<Address> result = findAddressByIdGateway.execute(address.getId());

        verify(addressEntityRepository).findById(address.getId());

        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(address);
    }
}