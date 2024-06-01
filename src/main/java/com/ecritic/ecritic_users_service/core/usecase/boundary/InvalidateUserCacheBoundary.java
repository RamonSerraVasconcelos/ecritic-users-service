package com.ecritic.ecritic_users_service.core.usecase.boundary;

import java.util.UUID;

public interface InvalidateUserCacheBoundary {

    void execute(UUID userId);
}
