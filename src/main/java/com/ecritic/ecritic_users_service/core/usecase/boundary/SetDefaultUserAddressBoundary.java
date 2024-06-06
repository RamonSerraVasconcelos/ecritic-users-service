package com.ecritic.ecritic_users_service.core.usecase.boundary;

import java.util.UUID;

public interface SetDefaultUserAddressBoundary {

    void execute(UUID userId, UUID addressId);
}
