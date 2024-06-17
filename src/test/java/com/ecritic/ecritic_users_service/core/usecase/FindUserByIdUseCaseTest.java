package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUserBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
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
class FindUserByIdUseCaseTest {

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private FindCachedUserBoundary findCachedUserBoundary;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Test
    void givenExecution_whenUserIsCached_thenReturnUserFromCache() {
        User user = UserFixture.load();

        when(findCachedUserBoundary.execute(user.getId())).thenReturn(Optional.of(user));

        User foundUser = findUserByIdUseCase.execute(user.getId());

        verify(findCachedUserBoundary).execute(user.getId());
        verifyNoInteractions(findUserByIdBoundary);

        assertThat(foundUser).isNotNull().usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void givenExecution_whenUserIsNotCached_thenReturnUserFromDB() {
        User user = UserFixture.load();

        when(findCachedUserBoundary.execute(user.getId())).thenReturn(Optional.empty());
        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));

        User foundUser = findUserByIdUseCase.execute(user.getId());

        verify(findCachedUserBoundary).execute(user.getId());
        verify(findUserByIdBoundary).execute(user.getId());

        assertThat(foundUser).isNotNull().usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void givenExecution_whenUserIsNotFound_thenThrowEntityNotFoundException() {
        User user = UserFixture.load();

        when(findCachedUserBoundary.execute(user.getId())).thenReturn(Optional.empty());
        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> findUserByIdUseCase.execute(user.getId()));

        verify(findCachedUserBoundary).execute(user.getId());
        verify(findUserByIdBoundary).execute(user.getId());

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }
}