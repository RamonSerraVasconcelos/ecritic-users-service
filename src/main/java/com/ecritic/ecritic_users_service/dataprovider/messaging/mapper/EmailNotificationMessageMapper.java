package com.ecritic.ecritic_users_service.dataprovider.messaging.mapper;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.EmailNotificationMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailNotificationMessageMapper {

    EmailNotificationMessage emailNotificationToEmailNotificationMessage(EmailNotification emailNotification);
}
