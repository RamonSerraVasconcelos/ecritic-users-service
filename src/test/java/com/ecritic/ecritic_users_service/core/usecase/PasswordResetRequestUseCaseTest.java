package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetRequestUseCaseTest {

    @InjectMocks
    private PasswordResetRequestUseCase passwordResetRequestUseCase;

    @Mock
    private FindUserByEmailBoundary findUserByEmailBoundary;

    @Mock
    private SaveUserBoundary saveUserBoundary;

    @Mock
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    @Test
    void givenValidEmail_thenUpdateUser_andSendMessage() {
        User user = UserFixture.load();
        String encodedHash = "2d6h2d0shf@837fs92#123";

        when(bcrypt.encode(anyString())).thenReturn(encodedHash);
        when(findUserByEmailBoundary.execute(user.getEmail())).thenReturn(user);
        when(saveUserBoundary.execute(user)).thenReturn(user);

        passwordResetRequestUseCase.execute(user.getEmail());

        verify(bcrypt).encode(anyString());
        verify(findUserByEmailBoundary).execute(user.getEmail());
        verify(saveUserBoundary).execute(user);
        verify(sendEmailNotificationUseCase).execute(any(UUID.class), anyString(), any(NotificationContentEnum.class), anyMap());
    }

    @Test
    void givenInvalidEmail_thenThrowEntityNotFoundException() {
        String email = "test@test.test";
        when(findUserByEmailBoundary.execute(email)).thenReturn(null);

        passwordResetRequestUseCase.execute(email);
        verify(findUserByEmailBoundary).execute(email);
        verifyNoInteractions(bcrypt);
        verifyNoInteractions(saveUserBoundary);
        verifyNoInteractions(sendEmailNotificationUseCase);
    }
}