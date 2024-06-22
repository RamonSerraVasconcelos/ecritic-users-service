package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressByIdBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserAddressUseCaseTest {

    @InjectMocks
    private FindUserAddressUseCase findUserAddressUseCase;

    @Mock
    private FindUserAddressByIdBoundary findUserAddressByIdBoundary;

    @Test
    void givenExecution_thenReturnUserAddress() {
        Address address = AddressFixture.load();
        UUID userId = UUID.randomUUID();

        when(findUserAddressByIdBoundary.execute(userId, address.getId())).thenReturn(Optional.of(address));

        Address foundAddress = findUserAddressUseCase.execute(userId, address.getId());

        verify(findUserAddressByIdBoundary).execute(userId, address.getId());
        assertThat(foundAddress).isEqualTo(address);
    }
}