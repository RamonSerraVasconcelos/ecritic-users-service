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
class SaveUserAddressGatewayTest {

    @InjectMocks
    private SaveUserAddressGateway saveUserAddressGateway;

    @Mock
    private AddressEntityRepository addressEntityRepository;

    @Test
    void givenExecution_thenSaveUserAddress() {
        UUID userId = UUID.randomUUID();
        UUID address = UUID.randomUUID();

        saveUserAddressGateway.execute(userId, address);

        verify(addressEntityRepository).saveUserAddress(userId, address);
    }
}