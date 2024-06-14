package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SendEmailNotificationBoundary;
import com.ecritic.ecritic_users_service.core.utils.NotificationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailNotificationUseCase {

    private final SendEmailNotificationBoundary sendEmailNotificationBoundary;

    public void execute(UUID userId, String email, NotificationContentEnum notificationContentEnum, Map<String, String> variables) {
        log.info("Sending email notification to user [{}] with subject id: [{}]", userId, notificationContentEnum.getNotificationId());

        String body = NotificationUtils.replaceVariables(notificationContentEnum.getBodyTemplate(), variables);

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setUserId(userId);
        emailNotification.setEmail(email);
        emailNotification.setNotificationContentEnum(notificationContentEnum);
        emailNotification.setBody(body);

        sendEmailNotificationBoundary.execute(emailNotification);
    }
}
