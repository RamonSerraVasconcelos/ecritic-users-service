package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CacheKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheUsersGateway implements CacheUsersBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public void execute(Page<User> users) {
        String cacheKey = CacheKeys.USERS_KEY.buildKey(String.valueOf(users.getNumber()), String.valueOf(users.getSize()));

        log.info("Saving users to cache with cacheKey: [{}}", cacheKey);

        try {
            String usersJson = objectMapper.writeValueAsString(users.getContent());

            jedis.set(cacheKey, usersJson);
            jedis.pexpire(cacheKey, TimeUnit.MINUTES.toMillis(10));
        } catch (Exception ex) {
            log.error("Error saving users to cache with cacheKey: [{}}", cacheKey, ex);
        }
    }
}
