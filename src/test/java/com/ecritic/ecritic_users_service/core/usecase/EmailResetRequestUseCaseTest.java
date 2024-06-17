package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailResetRequestUseCaseTest {
    @InjectMocks
    private EmailResetRequestUseCase emailResetRequestUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private FindUserByEmailBoundary findUserByEmailBoundary;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @Test
    void givenExecutionWithValidParameters_thenUpdateUser_andPostMessage() {
        User user = UserFixture.load();
        String newEmail = "newemail@test.test";
        String encryptedHash = "1&NDSJAN@*#$&@BN";

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(bcrypt.encode(anyString())).thenReturn(encryptedHash);
        when(saveUserBoundary.execute(any(User.class))).thenReturn(user);
        when(findUserByEmailBoundary.execute(newEmail)).thenReturn(null);

        emailResetRequestUseCase.execute(user.getId(), newEmail);

        verify(findUserByIdUseCase).execute(user.getId());
        verify(bcrypt).encode(anyString());
        verify(saveUserBoundary).execute(any(User.class));
        verify(sendEmailNotificationUseCase).execute(any(UUID.class), anyString(), any(NotificationContentEnum.class), anyMap());
    }

    @Test
    void givenExecutionWithValidParameters_whenNewEmailIsEqualToCurrentOne_thenThrowResourceConflictException() {
        User user = UserFixture.load();

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(null);

        EntityConflictException ex = assertThrows(EntityConflictException.class, () -> emailResetRequestUseCase.execute(user.getId(), user.getEmail()));

        verify(findUserByIdUseCase).execute(user.getId());
        verifyNoInteractions(bcrypt, saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_12.getCode());
    }

    @Test
    void givenExecutionWithValidParameters_whenEmailIsAlreadyRegistered_thenThrowResourceConflictException() {
        User user = UserFixture.load();

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(user);

        EntityConflictException ex = assertThrows(EntityConflictException.class, () -> emailResetRequestUseCase.execute(user.getId(), user.getEmail()));

        verify(findUserByIdUseCase).execute(user.getId());
        verify(findUserByEmailBoundary).execute(user.getEmail());
        verifyNoInteractions(bcrypt, saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_07.getCode());
    }
}