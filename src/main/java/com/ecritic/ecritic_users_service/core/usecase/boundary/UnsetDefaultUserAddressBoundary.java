package com.ecritic.ecritic_users_service.core.usecase.boundary;

import java.util.UUID;

public interface UnsetDefaultUserAddressBoundary {

    void execute(UUID userId);
}
