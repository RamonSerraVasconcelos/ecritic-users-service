package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUserBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CacheKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedUserGateway implements FindCachedUserBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<User> execute(UUID userId) {
        String cacheKey = CacheKeys.USER_KEY.buildKey(userId.toString());

        try {
            String userJson = jedis.get(cacheKey);

            if (userJson == null) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(userJson, User.class));
        } catch (Exception ex) {
            log.error("Error finding user with id: [{}]", userId, ex);
            return Optional.empty();
        }
    }
}
