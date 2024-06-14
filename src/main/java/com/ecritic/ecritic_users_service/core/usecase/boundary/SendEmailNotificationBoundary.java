package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.EmailNotification;

public interface SendEmailNotificationBoundary {

    void execute(EmailNotification emailNotification);
}
