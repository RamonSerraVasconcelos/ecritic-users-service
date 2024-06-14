package com.ecritic.ecritic_users_service.core.model;

import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Notification {

    protected UUID userId;
    protected NotificationContentEnum notificationContentEnum;
    protected String body;
}
