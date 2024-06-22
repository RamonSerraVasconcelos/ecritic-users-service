package com.ecritic.ecritic_users_service.core.fixture;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import org.springframework.data.domain.PageRequest;

public class UserFilterFixture {

    public static UserFilter load() {
        return UserFilter.builder()
                .pageable(PageRequest.of(0, 20))
                .active(true)
                .build();
    }

    public static UserFilter loadWithParams() {
        return UserFilter.builder()
                .pageable(PageRequest.of(0, 20))
                .active(true)
                .name("Ryan Gosling")
                .build();
    }
}
