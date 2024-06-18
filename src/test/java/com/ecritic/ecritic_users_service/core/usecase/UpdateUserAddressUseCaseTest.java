package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserAddressUseCaseTest {

    @InjectMocks
    private UpdateUserAddressUseCase updateUserAddressUseCase;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private UpdateAddressUseCase updateAddressUseCase;

    @Mock
    private SetDefaultUserAddressUseCase setDefaultUserAddressUseCase;

    private static final UUID USER_ID = UUID.randomUUID();

    @Test
    void givenExecution_thenUpdate_andReturnUserAddress() {
        Address address = AddressFixture.load();

        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.of(UserFixture.load()));
        when(updateAddressUseCase.execute(address.getId(), address)).thenReturn(address);

        Address updatedAddress = updateUserAddressUseCase.execute(USER_ID, address.getId(), address);

        verify(findUserByIdBoundary).execute(USER_ID);
        verify(updateAddressUseCase).execute(address.getId(), address);
        verifyNoInteractions(setDefaultUserAddressUseCase);

        assertThat(updatedAddress).usingRecursiveComparison().isEqualTo(address);
    }

    @Test
    void givenExecution_whenAddressIsDefault_thehSetAsDefaultAndReturnUserAddress() {
        Address address = AddressFixture.load();
        address.setDefault(true);

        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.of(UserFixture.load()));
        when(updateAddressUseCase.execute(address.getId(), address)).thenReturn(address);

        Address updatedAddress = updateUserAddressUseCase.execute(USER_ID, address.getId(), address);

        verify(findUserByIdBoundary).execute(USER_ID);
        verify(updateAddressUseCase).execute(address.getId(), address);
        verify(setDefaultUserAddressUseCase).execute(USER_ID, address.getId());

        assertThat(updatedAddress).usingRecursiveComparison().isEqualTo(address);
    }

    @Test
    void givenExecution_whenUserIsNotFound_thenThrowEntityNotFoundException() {
        Address address = AddressFixture.load();

        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateUserAddressUseCase.execute(USER_ID, address.getId(), address));

        verify(findUserByIdBoundary).execute(USER_ID);
        verifyNoInteractions(updateAddressUseCase, setDefaultUserAddressUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }
}