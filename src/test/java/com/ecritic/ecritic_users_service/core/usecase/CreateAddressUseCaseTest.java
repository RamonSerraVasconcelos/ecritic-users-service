package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseTest {

    @InjectMocks
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private FindCountryByIdBoundary findCountryByIdBoundary;

    @Mock
    private SaveAddressBoundary saveAddressBoundary;

    @Test
    void givenParams_thenCreateAndReturnAddress() {
        Address address = AddressFixture.load();

        when(findCountryByIdBoundary.execute(address.getCountry().getId())).thenReturn(Optional.of(address.getCountry()));
        when(saveAddressBoundary.execute(address)).thenReturn(address);

        Address result = createAddressUseCase.execute(address);

        verify(findCountryByIdBoundary).execute(address.getCountry().getId());
        verify(saveAddressBoundary).execute(address);

        assertThat(result).isEqualTo(address);
    }

    @Test
    void givenParams_whenCountryIsNotFound_thenThrowExecption() {
        Address address = AddressFixture.load();

        when(findCountryByIdBoundary.execute(address.getCountry().getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createAddressUseCase.execute(address));

        verify(findCountryByIdBoundary).execute(address.getCountry().getId());
        verifyNoInteractions(saveAddressBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_08.getCode());
    }
}