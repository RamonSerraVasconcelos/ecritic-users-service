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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordChangeUseCaseTest {

    @InjectMocks
    private PasswordChangeUseCase passwordChangeUseCase;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @Test
    void givenValidParameters_thenChangeUserPassword_andDeleteUserRefreshTokens() {
        User user = UserFixture.load();
        String newPassword = "newPassword";

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(bcrypt.matches(newPassword, user.getPassword())).thenReturn(false);
        when(bcrypt.encode(newPassword)).thenReturn("encodedPassword");
        when(saveUserBoundary.execute(user)).thenReturn(user);

        passwordChangeUseCase.execute(user.getId(), user.getPassword(), newPassword, newPassword);

        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches("bladerunner2049", "bladerunner2049");
        verify(bcrypt).matches(newPassword, "bladerunner2049");
        verify(bcrypt).encode(newPassword);
        verify(saveUserBoundary).execute(user);
        verify(sendEmailNotificationUseCase).execute(user.getId(), user.getEmail(), NotificationContentEnum.PASSWORD_CHANGE, null);
    }

    @Test
    void givenExecution_whenPasswordConfirmationDoesNotMatch_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        String newPassword = "newPassword";

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> passwordChangeUseCase.execute(user.getId(), user.getPassword(), newPassword, "testing"));

        verifyNoInteractions(findUserByIdBoundary, bcrypt, saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_14.getCode());
    }

    @Test
    void givenInvalidCurrentPassword_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        String newPassword = "newPassword";

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(BusinessViolationException.class, () -> passwordChangeUseCase.execute(user.getId(), user.getPassword(), newPassword, newPassword));

        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches("bladerunner2049", "bladerunner2049");

        verifyNoInteractions(saveUserBoundary, sendEmailNotificationUseCase);
    }

    @Test
    void givenExecution_whenNewPasswordIsEqualToCurrentOne_thenThrowBusinessViolationException() {
        User user = UserFixture.load();
        String newPassword = "newPassword";

        when(findUserByIdBoundary.execute(user.getId())).thenReturn(Optional.of(user));
        when(bcrypt.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(bcrypt.matches(newPassword, user.getPassword())).thenReturn(true);

        BusinessViolationException ex = assertThrows(BusinessViolationException.class, () -> passwordChangeUseCase.execute(user.getId(), user.getPassword(), newPassword, newPassword));

        verify(findUserByIdBoundary).execute(user.getId());
        verify(bcrypt).matches("bladerunner2049", "bladerunner2049");
        verify(bcrypt).matches(newPassword, "bladerunner2049");

        verifyNoInteractions(saveUserBoundary, sendEmailNotificationUseCase);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_13.getCode());
    }
}