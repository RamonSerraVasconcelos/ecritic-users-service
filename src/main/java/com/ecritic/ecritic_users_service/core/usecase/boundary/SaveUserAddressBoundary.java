package com.ecritic.ecritic_users_service.core.usecase.boundary;

import java.util.UUID;

public interface SaveUserAddressBoundary {

    void execute(UUID userId, UUID addressId);
}
