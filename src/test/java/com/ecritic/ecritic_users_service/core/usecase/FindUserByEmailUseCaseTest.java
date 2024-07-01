package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByEmailUseCaseTest {

    @InjectMocks
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Mock
    private FindUserByEmailBoundary findUserByEmailBoundary;

    @Test
    void givenExecution_thenFind_andReturnUser() {
        User user = UserFixture.load();

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(user);

        User result = findUserByEmailUseCase.execute(user.getEmail());

        verify(findUserByEmailBoundary).execute(user.getEmail());

        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void givenExecution_whenUserIsNotFound_thenThrowEntityNotFoundException() {
        when(findUserByEmailBoundary.execute(anyString())).thenReturn(null);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> findUserByEmailUseCase.execute(anyString()));

        verify(findUserByEmailBoundary).execute(anyString());
        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }
}