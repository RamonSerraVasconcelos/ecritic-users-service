package com.ecritic.ecritic_users_service.core.usecase.boundary;

import java.util.UUID;

public interface PostStatusUpdateMessageBoundary {

    void execute(UUID userId, boolean active);
}
