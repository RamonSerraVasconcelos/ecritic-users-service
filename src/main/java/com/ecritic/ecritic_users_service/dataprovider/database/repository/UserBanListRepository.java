package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.BanActionEntity;

import java.util.UUID;

public interface UserBanListRepository {

    void updateBanList(UUID userId, String motive, BanActionEntity action);
}
