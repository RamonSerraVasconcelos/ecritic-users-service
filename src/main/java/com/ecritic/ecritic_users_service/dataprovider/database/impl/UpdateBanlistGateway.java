package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.UpdateBanListBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.BanActionEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserBanListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateBanlistGateway implements UpdateBanListBoundary {

    private final UserBanListRepository userBanListRepository;

    public void execute(UUID userId, String motive, BanActionEnum action) {
        userBanListRepository.updateBanList(userId, motive, BanActionEntity.valueOf(action.name()));
    }
}
