package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersCountBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CountUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersCountBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountUsersUseCase {

    private final FindCachedUsersCountBoundary findCachedUsersCountBoundary;

    private final CountUsersBoundary countUsersBoundary;

    private final CacheUsersCountBoundary cacheUsersCountBoundary;

    public Long execute(UserFilter userFilter) {
        try {
            log.info("Counting all users");

            if (userFilter.isCacheable()) {
                Long cachedCount = findCachedUsersCountBoundary.execute();

                if (nonNull(cachedCount)) {
                    return cachedCount;
                }
            }

            Long count = countUsersBoundary.execute(userFilter);

            if (userFilter.isCacheable()) cacheUsersCountBoundary.execute(count);

            return count;
        } catch (Exception e) {
            log.error("Error counting users", e);
            throw e;
        }
    }
}
