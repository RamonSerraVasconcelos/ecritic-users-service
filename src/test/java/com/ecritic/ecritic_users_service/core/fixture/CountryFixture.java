package com.ecritic.ecritic_users_service.core.fixture;

import com.ecritic.ecritic_users_service.core.model.Country;

public class CountryFixture {

    public static Country load() {
        return Country.builder()
                .id(19L)
                .name("Brazil")
                .build();
    }
}
