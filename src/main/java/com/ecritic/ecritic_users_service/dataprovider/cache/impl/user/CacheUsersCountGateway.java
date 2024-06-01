package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersCountBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CacheKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheUsersCountGateway implements CacheUsersCountBoundary {

    private final Jedis jedis;

    @Override
    public void execute(Long count) {
        String cacheKey = CacheKeys.USERS_COUNT_KEY.getKey();

        log.info("Saving users count to redis cache with cacheKey: [{}]", cacheKey);

        try {
            jedis.set(cacheKey, String.valueOf(count));
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(10));
        } catch (Exception e) {
            log.error("Error when saving users count to redis cache with cacheKey: [{}]", cacheKey, e);
        }
    }
}
