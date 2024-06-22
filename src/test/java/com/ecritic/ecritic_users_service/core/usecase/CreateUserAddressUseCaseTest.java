package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserAddressBoundary;
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
class CreateUserAddressUseCaseTest {

    @InjectMocks
    private CreateUserAddressUseCase createUserAddressUseCase;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private SaveUserAddressBoundary saveUserAddressBoundary;

    @Mock
    private SetDefaultUserAddressUseCase setDefaultUserAddressUseCase;

    @Test
    void givenExecution_whenUserIsValid_thenCreate_andReturnResult() {
        User user = UserFixture.load();
        Address address = AddressFixture.load();

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(createAddressUseCase.execute(address)).thenReturn(address);

        Address result = createUserAddressUseCase.execute(user.getId(), address);

        verify(findUserByIdBoundary).execute(user.getId());
        verify(createAddressUseCase).execute(address);
        verify(saveUserAddressBoundary).execute(user.getId(), address.getId());
        verifyNoInteractions(setDefaultUserAddressUseCase);

        assertThat(result).usingRecursiveComparison().isEqualTo(address);
    }

    @Test
    void givenAddressCreation_whenAddressIsDefault_thenSetAsDefault_andReturnResult() {
        User user = UserFixture.load();
        Address address = AddressFixture.load();
        address.setDefault(true);

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(createAddressUseCase.execute(address)).thenReturn(address);

        Address result = createUserAddressUseCase.execute(user.getId(), address);

        verify(findUserByIdBoundary).execute(user.getId());
        verify(createAddressUseCase).execute(address);
        verify(saveUserAddressBoundary).execute(user.getId(), address.getId());
        verify(setDefaultUserAddressUseCase).execute(user.getId(), address.getId());

        assertThat(result).usingRecursiveComparison().isEqualTo(address);
    }

    @Test
    void givenExecution_whenUserIsInvalid_thenThrowException() {
        Address address = AddressFixture.load();

        when(findUserByIdBoundary.execute(address.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createUserAddressUseCase.execute(address.getId(), address));

        verify(findUserByIdBoundary).execute(address.getId());
        verifyNoInteractions(createAddressUseCase);
        verifyNoInteractions(saveUserAddressBoundary);
        verifyNoInteractions(setDefaultUserAddressUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }
}