package com.ecritic.ecritic_users_service.dataprovider.messaging.mapper;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.EmailNotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationMessageMapper {

    public EmailNotificationMessage emailNotificationToEmailNotificationMessage(EmailNotification emailNotification) {
        return EmailNotificationMessage.builder()
                .userId(emailNotification.getUserId())
                .email(emailNotification.getEmail())
                .notificationSubjectId(emailNotification.getNotificationContentEnum().getNotificationId())
                .subject(emailNotification.getNotificationContentEnum().getSubject())
                .body(emailNotification.getBody())
                .build();
    }
}
