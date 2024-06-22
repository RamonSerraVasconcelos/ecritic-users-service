package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindAddressByIdBoundary;
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
class UpdateAddressUseCaseTest {

    @InjectMocks
    private UpdateAddressUseCase updateAddressUseCase;

    @Mock
    private FindCountryByIdBoundary findCountryByIdBoundary;

    @Mock
    private SaveAddressBoundary saveAddressBoundary;

    @Mock
    private FindAddressByIdBoundary findAddressByIdBoundary;

    @Test
    void givenExecution_thenUpdate_andReturnAddress() {
        Address address = AddressFixture.load();

        when(findCountryByIdBoundary.execute(address.getCountry().getId())).thenReturn(Optional.of(address.getCountry()));
        when(findAddressByIdBoundary.execute(address.getId())).thenReturn(Optional.of(address));
        when(saveAddressBoundary.execute(address)).thenReturn(address);

        Address updatedAddress = updateAddressUseCase.execute(address.getId(), address);

        verify(findCountryByIdBoundary).execute(address.getCountry().getId());
        verify(findAddressByIdBoundary).execute(address.getId());
        verify(saveAddressBoundary).execute(address);

        assertThat(updatedAddress).isEqualTo(address);
    }

    @Test
    void givenExecution_whenCountryIsNotFound_thenThrowEntityNotFoundException() {
        Address address = AddressFixture.load();

        when(findCountryByIdBoundary.execute(address.getCountry().getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateAddressUseCase.execute(address.getId(), address));

        verify(findCountryByIdBoundary).execute(address.getCountry().getId());
        verifyNoInteractions(saveAddressBoundary, findAddressByIdBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_08.getCode());
    }

    @Test
    void givenExecution_whenAddressIsNotFound_thenThrowEntityNotFoundException() {
        Address address = AddressFixture.load();

        when(findCountryByIdBoundary.execute(address.getCountry().getId())).thenReturn(Optional.of(address.getCountry()));
        when(findAddressByIdBoundary.execute(address.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateAddressUseCase.execute(address.getId(), address));

        verify(findCountryByIdBoundary).execute(address.getCountry().getId());
        verify(findAddressByIdBoundary).execute(address.getId());
        verifyNoInteractions(saveAddressBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_10.getCode());
    }
}