package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserStatusGatewayTest {

    @InjectMocks
    private UpdateUserStatusGateway updateUserStatusGateway;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Test
    void givenExecution_thenUpdateUserStatus() {
        UUID userId = UUID.randomUUID();

        updateUserStatusGateway.execute(userId, true);

        verify(userEntityRepository).updateStatus(userId, true);
    }
}