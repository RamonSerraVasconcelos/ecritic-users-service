package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.usecase.boundary.SetDefaultUserAddressBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.UnsetDefaultUserAddressBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetDefaultUserAddressUseCase {

    private final UnsetDefaultUserAddressBoundary unsetDefaultUserAddressBoundary;

    private final SetDefaultUserAddressBoundary setDefaultUserAddressBoundary;

    public void execute(UUID userId, UUID addressId) {
        log.info("Setting default address for user with id: [{}} and addressId: [{}]", userId, addressId);

        try {
            unsetDefaultUserAddressBoundary.execute(userId);
            setDefaultUserAddressBoundary.execute(userId, addressId);
        } catch (Exception ex) {
            log.error("Error while setting default address for user with id: [{}} and addressId: [{}]", userId, addressId, ex);
            throw ex;
        }
    }
}
