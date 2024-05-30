package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersCountBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedUsersCountGateway implements FindCachedUsersCountBoundary {

    private final Jedis jedis;

    @Override
    public Long execute() {
        String cacheKey = "users-count";

        log.info("Finding users count from redis cache with cacheKey: [{}]", cacheKey);

        try {
            String countValue = jedis.get(cacheKey);

            return nonNull(countValue) ? Long.parseLong(countValue) : null;
        } catch (Exception e) {
            log.error("Error when finding users count from redis cache with cacheKey: [{}]", cacheKey, e);
            return null;
        }
    }
}
