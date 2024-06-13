package com.ecritic.ecritic_users_service.core.model;

import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Notification {

    protected Long userId;
    protected NotificationContentEnum notificationContentEnum;
    protected String body;
}
