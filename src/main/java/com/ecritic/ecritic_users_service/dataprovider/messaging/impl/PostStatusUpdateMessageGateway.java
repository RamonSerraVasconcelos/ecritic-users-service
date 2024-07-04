package com.ecritic.ecritic_users_service.dataprovider.messaging.impl;

import com.ecritic.ecritic_users_service.core.usecase.boundary.PostStatusUpdateMessageBoundary;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.UserStatusUpdateMessage;
import com.ecritic.ecritic_users_service.dataprovider.messaging.producer.UserStatusUpdateProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostStatusUpdateMessageGateway implements PostStatusUpdateMessageBoundary {

    private final UserStatusUpdateProducer userStatusUpdateProducer;

    public void execute(UUID userId, boolean active) {
        UserStatusUpdateMessage userStatusUpdateMessage = UserStatusUpdateMessage.builder()
                .userId(userId)
                .active(active)
                .build();

        userStatusUpdateProducer.execute(userStatusUpdateMessage);
    }
}
