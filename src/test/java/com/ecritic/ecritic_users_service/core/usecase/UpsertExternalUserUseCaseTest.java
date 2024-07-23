package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UpsertExternalUserUseCaseTest {

    @InjectMocks
    private UpsertExternalUserUseCase externalUserUseCase;

    @Mock
    private FindUserByEmailBoundary findUserByEmailBoundary;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Test
    void givenParams_whenUserIsAlreadyRegistered_thenReturnUser() {
        User user = UserFixture.load();

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(user);

        User result = externalUserUseCase.execute(user.getEmail(), user.getName());

        verify(findUserByEmailBoundary).execute(user.getEmail());
        verifyNoInteractions(saveUserBoundary);
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void givenParams_whenUserIsNotRegistered_thenSaveAndReturnUser() {
        User user = UserFixture.load();

        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(null);
        when(saveUserBoundary.execute(any())).thenReturn(user);

        User result = externalUserUseCase.execute(user.getEmail(), user.getName());

        verify(findUserByEmailBoundary).execute(user.getEmail());
        verify(saveUserBoundary).execute(any(User.class));
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }
}