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
public class UserStatusUpdateMessage {

    private UUID userId;
    private boolean active;
}
