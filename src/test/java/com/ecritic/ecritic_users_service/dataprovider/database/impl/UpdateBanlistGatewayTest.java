package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.BanActionEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserBanListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateBanlistGatewayTest {

    @InjectMocks
    private UpdateBanlistGateway updateBanlistGateway;

    @Mock
    private UserBanListRepository userBanListRepository;

    @Test
    void givenExecution_thenUpdateBanList() {
        UUID userId = UUID.randomUUID();
        updateBanlistGateway.execute(userId, "motive", BanActionEnum.BAN);

        verify(userBanListRepository).updateBanList(userId, "motive", BanActionEntity.BAN);
    }
}