package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUserBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CacheKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheUserGateway implements CacheUserBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public void execute(User user) {
        String cacheKey = CacheKeys.USER_KEY.buildKey(user.getId().toString());

        log.info("Saving user to cache with cacheKey: [{}]", cacheKey);

        try {
            String movieString = objectMapper.writeValueAsString(user);

            jedis.set(cacheKey, movieString);
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(10));
        } catch (Exception ex) {
            log.error("Error saving user with id: [{}] to cache", user.getId(), ex);
        }
    }
}
