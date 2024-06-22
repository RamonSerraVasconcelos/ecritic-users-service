package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.usecase.boundary.SetDefaultUserAddressBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.UnsetDefaultUserAddressBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SetDefaultUserAddressUseCaseTest {

    @InjectMocks
    private SetDefaultUserAddressUseCase setDefaultUserAddressUseCase;

    @Mock
    private UnsetDefaultUserAddressBoundary unsetDefaultUserAddressBoundary;

    @Mock
    private SetDefaultUserAddressBoundary setDefaultUserAddressBoundary;

    @Test
    void givenExecution_thenSetDefaultUserAddress() {
        UUID userId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        setDefaultUserAddressUseCase.execute(userId, addressId);

        verify(unsetDefaultUserAddressBoundary).execute(userId);
        verify(setDefaultUserAddressBoundary).execute(userId, addressId);
    }
}