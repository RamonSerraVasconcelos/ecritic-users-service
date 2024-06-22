package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetUseCaseTest {

    @InjectMocks
    private PasswordResetUseCase passwordResetUseCase;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    private static final String PASSWORD_RESET_HASH = "jd989123dj9pd90ydasdhjdp98PSKASCHB";
    private static final String PASSWORD = "12345678";

    @Test
    void givenValidParameters_thenUpdateUserAndPostPasswordResetMessage() {
        User user = UserFixture.load();
        user.setPasswordResetHash("test");
        user.setPasswordResetDate(LocalDateTime.now().plusMinutes(5));

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(PASSWORD_RESET_HASH, user.getPasswordResetHash())).thenReturn(true);
        when(bcrypt.matches(PASSWORD, user.getPassword())).thenReturn(false);

        passwordResetUseCase.execute(user.getId(), PASSWORD_RESET_HASH, PASSWORD, PASSWORD);

        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches(PASSWORD_RESET_HASH, "test");
        verify(bcrypt).matches(PASSWORD, "bladerunner2049");
        verify(saveUserBoundary).execute(user);
        verify(sendEmailNotificationUseCase).execute(user.getId(), user.getEmail(), NotificationContentEnum.PASSWORD_RESET, null);
    }

    @Test
    void givenUnmachedPasswords_thenThrowBusinessViolationException() {
        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> passwordResetUseCase.execute(UUID.randomUUID(), PASSWORD_RESET_HASH, PASSWORD, "test"));

        verifyNoInteractions(findUserByIdBoundary, saveUserBoundary, bcrypt, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_14.getCode());
    }

    @Test
    void givenInvalidEmail_thenThrowBusinessViolationException() {
        UUID userId = UUID.randomUUID();

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> passwordResetUseCase.execute(userId, PASSWORD_RESET_HASH, PASSWORD, PASSWORD));

        verify(findUserByIdBoundary).execute(userId);
        verifyNoInteractions(bcrypt);
        verifyNoInteractions(saveUserBoundary);
        verifyNoInteractions(sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_01.getCode());
    }

    @Test
    void givenInvalidPasswordResetHash_thenThrowBusinessViolationException() {
        UUID userId = UUID.randomUUID();

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> passwordResetUseCase.execute(userId, PASSWORD_RESET_HASH, PASSWORD, PASSWORD));

        verify(findUserByIdBoundary).execute(userId);
        verifyNoInteractions(bcrypt);
        verifyNoInteractions(saveUserBoundary);
        verifyNoInteractions(sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_01.getCode());
    }

    @Test
    void givenOutdatedPasswordResetHash_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        user.setPasswordResetHash("test");
        user.setPasswordResetDate(LocalDateTime.now().minusMinutes(5));

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(PASSWORD_RESET_HASH, user.getPasswordResetHash())).thenReturn(true);

        assertThrows(BusinessViolationException.class, () -> passwordResetUseCase.execute(user.getId(), PASSWORD_RESET_HASH, PASSWORD, PASSWORD));
        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches(PASSWORD_RESET_HASH, "test");
        verifyNoInteractions(saveUserBoundary);
        verifyNoInteractions(sendEmailNotificationUseCase);
    }

    @Test
    void givenDuplicatedNewPassword_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        user.setPasswordResetHash("test");
        user.setPasswordResetDate(LocalDateTime.now().plusMinutes(5));

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(PASSWORD_RESET_HASH, user.getPasswordResetHash())).thenReturn(true);
        when(bcrypt.matches(PASSWORD, user.getPassword())).thenReturn(true);

        assertThrows(BusinessViolationException.class, () -> passwordResetUseCase.execute(user.getId(), PASSWORD_RESET_HASH, PASSWORD, PASSWORD));
        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches(PASSWORD_RESET_HASH, "test");
        verify(bcrypt).matches(PASSWORD, "bladerunner2049");
        verifyNoInteractions(saveUserBoundary);
        verifyNoInteractions(sendEmailNotificationUseCase);
    }
}