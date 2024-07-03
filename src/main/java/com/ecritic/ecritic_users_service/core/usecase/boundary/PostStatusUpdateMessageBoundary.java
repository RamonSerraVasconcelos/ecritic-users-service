package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;

import java.util.UUID;

public interface PostStatusUpdateMessageBoundary {

    void execute(UUID userId, BanActionEnum action);
}
