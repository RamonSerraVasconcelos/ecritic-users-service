package com.ecritic.ecritic_users_service.dataprovider.database.fixture;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;

public class CountryEntityFixture {

    public static CountryEntity load() {
        return CountryEntity.builder()
                .id(19L)
                .name("Brazil")
                .build();
    }
}
