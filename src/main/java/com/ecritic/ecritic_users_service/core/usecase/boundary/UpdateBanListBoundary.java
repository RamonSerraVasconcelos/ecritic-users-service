package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;

import java.util.UUID;

public interface UpdateBanListBoundary {

    void execute(UUID userId, String motive, BanActionEnum action);
}
