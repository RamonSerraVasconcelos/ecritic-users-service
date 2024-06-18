package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.Role;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUserCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private FindCountryByIdBoundary findCountryByIdBoundary;

    @Mock
    private InvalidateUsersCacheBoundary invalidateUsersCacheBoundary;

    @Mock
    private InvalidateUserCacheBoundary invalidateUserCacheBoundary;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void givenValidUser_thenCallUpdateUserBoundary_andReturnUpdatedUser() {
        User user = UserFixture.load();
        User foundUser = UserFixture.load();

        user.setName("new name");
        user.setDescription("new description");

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(foundUser));
        when(findCountryByIdBoundary.execute(user.getCountry().getId())).thenReturn(Optional.of(foundUser.getCountry()));
        when(saveUserBoundary.execute(any())).thenReturn(user);

        User updatedUser = updateUserUseCase.execute(user.getId(), user);

        verify(findUserByIdBoundary).execute(user.getId());
        verify(findCountryByIdBoundary).execute(foundUser.getCountry().getId());
        verify(saveUserBoundary).execute(any());
        verify(invalidateUsersCacheBoundary).execute();
        verify(invalidateUserCacheBoundary).execute(user.getId());

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo(user.getName());
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(updatedUser.getDescription()).isEqualTo(user.getDescription());
        assertThat(updatedUser.isActive()).isEqualTo(user.isActive());
        assertThat(updatedUser.getRole()).isEqualTo(user.getRole());
        assertThat(updatedUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(updatedUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
    }

    @Test
    void givenValidUser_whenExecuteUseCase_dontCallBoundaryWithUnauthorizedFields() {
        User user = UserFixture.load();
        User foundUser = UserFixture.load();

        user.setName("new name");
        user.setDescription("new description");

        user.setEmail("shouldnotupdateemail@test.test");
        user.setPassword("should not update password");
        user.setActive(false);
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDateTime.now().plusDays(100));

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(foundUser));
        when(findCountryByIdBoundary.execute(user.getCountry().getId())).thenReturn(Optional.of(foundUser.getCountry()));
        when(saveUserBoundary.execute(userArgumentCaptor.capture())).thenReturn(any());

        updateUserUseCase.execute(user.getId(), user);

        User userToBeUpdated = userArgumentCaptor.getValue();

        assertNotEquals(userToBeUpdated.getEmail(), user.getEmail());
        assertNotEquals(userToBeUpdated.getPassword(), user.getPassword());
        assertNotEquals(userToBeUpdated.isActive(), user.isActive());
        assertNotEquals(userToBeUpdated.getRole(), user.getRole());
        assertNotEquals(userToBeUpdated.getCreatedAt(), user.getCreatedAt());
    }

    @Test
    void givenExecution_whenUserIsNotFound_thenThrowEntityNotFoundException() {
        User user = UserFixture.load();

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateUserUseCase.execute(user.getId(), user));

        verify(findUserByIdBoundary).execute(user.getId());
        verifyNoInteractions(saveUserBoundary, findCountryByIdBoundary, invalidateUsersCacheBoundary, invalidateUserCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }

    @Test
    void givenExecution_whenCountryIsNotFound_thenThrowEntityNotFoundException() {
        User user = UserFixture.load();

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(findCountryByIdBoundary.execute(user.getCountry().getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateUserUseCase.execute(user.getId(), user));

        verify(findUserByIdBoundary).execute(user.getId());
        verify(findCountryByIdBoundary).execute(user.getCountry().getId());
        verifyNoInteractions(saveUserBoundary, invalidateUsersCacheBoundary, invalidateUserCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_08.getCode());
    }
}