package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressesBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserAddressesUseCaseTest {

    @InjectMocks
    private FindUserAddressesUseCase findUserAddressesUseCase;

    @Mock
    private FindUserAddressesBoundary findUserAddressesBoundary;

    @Test
    void givenExecution_thenFind_andReturnAddresses() {
        UUID userId = UUID.randomUUID();

        when(findUserAddressesBoundary.execute(userId)).thenReturn(List.of(AddressFixture.load(), AddressFixture.load()));

        List<Address> addresses = findUserAddressesUseCase.execute(userId);

        verify(findUserAddressesBoundary).execute(userId);
        assertThat(addresses).isNotEmpty().hasSize(2);
    }
}