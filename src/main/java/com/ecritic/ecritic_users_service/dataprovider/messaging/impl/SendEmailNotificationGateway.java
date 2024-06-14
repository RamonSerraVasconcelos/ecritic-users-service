package com.ecritic.ecritic_users_service.dataprovider.messaging.impl;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SendEmailNotificationBoundary;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.EmailNotificationMessage;
import com.ecritic.ecritic_users_service.dataprovider.messaging.mapper.EmailNotificationMessageMapper;
import com.ecritic.ecritic_users_service.dataprovider.messaging.producer.EmailNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailNotificationGateway implements SendEmailNotificationBoundary {

    private final EmailNotificationProducer sendEmailNotificationProducer;

    private final EmailNotificationMessageMapper emailNotificationMessageMapper;

    public void execute(EmailNotification emailNotification) {
        EmailNotificationMessage emailNotificationMessage = emailNotificationMessageMapper.emailNotificationToEmailNotificationMessage(emailNotification);

        sendEmailNotificationProducer.execute(emailNotificationMessage);
    }
}
