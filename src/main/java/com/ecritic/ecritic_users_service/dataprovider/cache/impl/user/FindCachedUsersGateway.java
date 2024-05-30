package com.ecritic.ecritic_users_service.dataprovider.cache.impl.user;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersBoundary;
import com.ecritic.ecritic_users_service.dataprovider.cache.CachingUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindCachedUsersGateway implements FindCachedUsersBoundary {

    private final Jedis jedis;

    private final ObjectMapper objectMapper;

    @Override
    public List<User> execute(Pageable pageable) {
        String cacheKey = CachingUtils.buildPaginationCachekey("users", pageable.getPageNumber(), pageable.getPageSize());

        log.info("Retrieving users from cache with key: {}", cacheKey);

        try {
            String usersJson = jedis.get(cacheKey);

            if (isNull(usersJson) || usersJson.isBlank()) {
                return List.of();
            }

            return objectMapper.readValue(usersJson, new TypeReference<>() {});
        } catch (Exception ex) {
            log.error("Error while retrieving users from cache: {}", ex.getMessage());
            return List.of();
        }
    }
}
