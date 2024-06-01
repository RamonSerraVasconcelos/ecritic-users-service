package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUsersByParamsBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUsersUseCase {

    private final FindCachedUsersBoundary findCachedUsersBoundary;

    private final FindUsersByParamsBoundary findUsersByParamsBoundary;

    private final CountUsersUseCase countUsersUseCase;

    private final CacheUsersBoundary cacheUsersBoundary;

    private boolean shouldCacheUsers;

    public Page<User> execute(UserFilter userFilter) {
        log.info("Finding all users with parameters: [{}]", userFilter.toString());

        try {
            List<User> users = getUsers(userFilter);

            Long usersCount = countUsersUseCase.execute(userFilter);

            Page<User> pageUsers = new PageImpl<>(users, userFilter.getPageable(), usersCount);

            if (userFilter.isCacheable() && shouldCacheUsers) {
                cacheUsersBoundary.execute(pageUsers);
            }

            return pageUsers;
        } catch (Exception ex) {
            log.error("Error finding users", ex);
            throw ex;
        }
    }

    private List<User> getUsers(UserFilter userFilter) {
        List<User> users = new ArrayList<>();

        if (userFilter.isCacheable()) {
            users = findCachedUsersBoundary.execute(userFilter.getPageable());
            shouldCacheUsers = false;
        }

        if (users.isEmpty()) {
            log.info("Users not found on cache, fetching from database...");

            users = findUsersByParamsBoundary.execute(userFilter);
            shouldCacheUsers = true;
        }

        return users;
    }
}
