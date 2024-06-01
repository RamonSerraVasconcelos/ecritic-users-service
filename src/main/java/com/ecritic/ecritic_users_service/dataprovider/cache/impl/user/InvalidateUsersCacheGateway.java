package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCache;
import com.ecritic.ecritic_users_service.dataprovider.cache.InvalidatePaginationCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateUsersCacheGateway implements InvalidateUsersCache {

    private final InvalidatePaginationCache invalidatePaginationCache;

    @Override
    public void execute() {
        try {
            log.info("Invalidating users cache");

            invalidatePaginationCache.execute("users");
        } catch (Exception e) {
            log.error("Error invalidating users cache", e);
        }
    }
}
