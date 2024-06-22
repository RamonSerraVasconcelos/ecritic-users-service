package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailResetUseCaseTest {

    @InjectMocks
    private EmailResetUseCase emailResetUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @Test
    void givenValidParameters_thenChangeUserEmail() {
        User user = UserFixture.load();
        user.setEmailResetHash("emailResetHash");
        user.setEmailResetDate(LocalDateTime.now().plusMinutes(10));

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(bcrypt.matches(anyString(), anyString())).thenReturn(true);
        when(saveUserBoundary.execute(any(User.class))).thenReturn(user);

        emailResetUseCase.execute(user.getId(), "newemail@test.test");

        verify(findUserByIdUseCase).execute(user.getId());
        verify(bcrypt).matches(anyString(), anyString());
        verify(saveUserBoundary).execute(any(User.class));

        verify(sendEmailNotificationUseCase).execute(user.getId(), user.getEmail(), NotificationContentEnum.EMAIL_RESET, null);
    }

    @Test
    void givenExecutionWithInvalidEmailResetHash_thenThrowResourceViolationException() {
        User user = UserFixture.load();
        user.setEmailResetHash("emailResetHash");
        user.setEmailResetDate(LocalDateTime.now().plusMinutes(10));

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(bcrypt.matches(anyString(), anyString())).thenReturn(false);

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> emailResetUseCase.execute(user.getId(), "newemail@test.test"));

        verify(findUserByIdUseCase).execute(user.getId());
        verify(bcrypt).matches(anyString(), anyString());
        verifyNoInteractions(saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_01.getCode());
    }

    @Test
    void givenExecutionWithExpiredEmailResetLink_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        user.setEmailResetHash("emailResetHash");
        user.setEmailResetDate(LocalDateTime.now().minusMinutes(10));

        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(bcrypt.matches(anyString(), anyString())).thenReturn(true);

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> emailResetUseCase.execute(user.getId(), "newemail@test.test"));

        verify(findUserByIdUseCase).execute(user.getId());
        verify(bcrypt).matches(anyString(), anyString());
        verifyNoInteractions(saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_01.getCode());
    }

    @Test
    void givenException_thenThrowBusinessViolationException() {
        doThrow(RuntimeException.class).when(findUserByIdUseCase).execute(any());

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> emailResetUseCase.execute(UUID.randomUUID(), "newemail@test.test"));

        verify(findUserByIdUseCase).execute(any(UUID.class));
        verifyNoInteractions(bcrypt, saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_01.getCode());
    }
}