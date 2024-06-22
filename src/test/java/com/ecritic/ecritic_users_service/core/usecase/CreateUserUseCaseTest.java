package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private FindUserByEmailBoundary findUserByEmailBoundary;

    @Mock
    private FindCountryByIdBoundary findCountryByIdBoundary;

    @Mock
    private InvalidateUsersCacheBoundary invalidateUsersCacheBoundary;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @Test
    void givenValidEmailAndPassword_thenCreateAndReturnUser() {
        User user = UserFixture.load();
        String initialPassword = user.getPassword();
        String encodedPassword = "2d6h2d0shf@837fs92#123";

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(null);
        when(findCountryByIdBoundary.execute(user.getCountry().getId())).thenReturn(Optional.of(user.getCountry()));
        when(bcrypt.encode(user.getPassword())).thenReturn(encodedPassword);
        when(saveUserBoundary.execute(user)).thenReturn(user);

        User createdUser = createUserUseCase.execute(user, initialPassword);

        verify(findUserByEmailBoundary).execute(user.getEmail());
        verify(findCountryByIdBoundary).execute(user.getCountry().getId());
        verify(bcrypt).encode(initialPassword);
        verify(saveUserBoundary).execute(user);
        verify(invalidateUsersCacheBoundary).execute();

        assertThat(createdUser).isNotNull().usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void givenUnmatchedPassword_thenThrowBusinessViolationException() {
        User user = UserFixture.load();

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> createUserUseCase.execute(user, "password"));

        verifyNoInteractions(saveUserBoundary, findUserByEmailBoundary, findCountryByIdBoundary, bcrypt, invalidateUsersCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_14.getCode());
    }

    @Test
    void givenDuplicatedEmail_thenThrowEntityConflictException() {
        User user = UserFixture.load();

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(user);

        EntityConflictException ex = assertThrows(EntityConflictException.class, () -> createUserUseCase.execute(user, user.getPassword()));

        verify(findUserByEmailBoundary).execute(user.getEmail());
        verifyNoInteractions(saveUserBoundary, findCountryByIdBoundary, bcrypt, invalidateUsersCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_07.getCode());
    }

    @Test
    void givenInvalidCountry_thenThrowEntityNotFoundException() {
        User user = UserFixture.load();

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(null);
        when(findCountryByIdBoundary.execute(user.getCountry().getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> createUserUseCase.execute(user, user.getPassword()));

        verify(findUserByEmailBoundary).execute(user.getEmail());
        verify(findCountryByIdBoundary).execute(user.getCountry().getId());
        verifyNoInteractions(saveUserBoundary, bcrypt, invalidateUsersCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_08.getCode());
    }
}