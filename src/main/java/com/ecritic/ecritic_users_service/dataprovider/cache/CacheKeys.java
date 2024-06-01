package com.ecritic.ecritic_users_service.dataprovider.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeys {

    USERS_KEY("users:%s:%s"),
    USERS_COUNT_KEY("users-count"),
    USER_KEY("user:%s");

    private final String key;

    public String buildKey(String... replacements) {
        return String.format(key, (Object) replacements);
    }
}
