package com.ecritic.ecritic_users_service.dataprovider.messaging.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationMessage {

    private UUID userId;
    private String notificationSubjectId;
    private String email;
    private String subject;
    private String body;
}
