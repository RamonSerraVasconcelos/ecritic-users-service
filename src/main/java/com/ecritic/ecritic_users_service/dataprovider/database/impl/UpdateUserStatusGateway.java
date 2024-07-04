package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.usecase.boundary.UpdateUserStatusBoundary;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateUserStatusGateway implements UpdateUserStatusBoundary {

    private final UserEntityRepository userEntityRepository;

    public void execute(UUID id, boolean active) {
        userEntityRepository.updateStatus(id, active);
    }
}
