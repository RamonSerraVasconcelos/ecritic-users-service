package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnsetDefaultUserAddressGatewayTest {

    @InjectMocks
    private UnsetDefaultUserAddressGateway unsetDefaultUserAddressGateway;

    @Mock
    private AddressEntityRepository addressEntityRepository;

    @Test
    void givenExecution_unsetDefaultUserAddress() {
        UUID userId = UUID.randomUUID();

        unsetDefaultUserAddressGateway.execute(userId);

        verify(addressEntityRepository).unsetUserDefaultAddress(userId);
    }
}