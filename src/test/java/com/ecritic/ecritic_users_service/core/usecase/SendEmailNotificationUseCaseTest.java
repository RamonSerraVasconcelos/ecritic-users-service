package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SendEmailNotificationBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendEmailNotificationUseCaseTest {

    @InjectMocks
    private SendEmailNotificationUseCase sendEmailNotificationUseCase;

    @Mock
    private SendEmailNotificationBoundary sendEmailNotificationBoundary;

    @Test
    void givenExecution_ThenPrepare_AndSendEmail() {
        UUID userId = UUID.randomUUID();
        String email = "a@a.com";

        Map<String, String> variables = new HashMap<>();
        variables.put("userId", userId.toString());
        variables.put("emailResetHash", "hash");

        sendEmailNotificationUseCase.execute(userId, email, NotificationContentEnum.EMAIL_RESET_REQUEST, variables);

        verify(sendEmailNotificationBoundary).execute(any(EmailNotification.class));
    }
}