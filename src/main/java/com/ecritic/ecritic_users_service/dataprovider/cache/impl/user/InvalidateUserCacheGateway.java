package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUserCacheBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidateUserCacheGateway implements InvalidateUserCacheBoundary {

    private final Jedis jedis;

    @Override
    public void execute(UUID userId) {
        String cacheKey = CacheKeys.USER_KEY.buildKey(userId.toString());

        try {
            jedis.del(cacheKey);
        } catch (Exception ex) {
            log.error("Error invalidating user cache with id: [{}]", userId, ex);
        }
    }
}
