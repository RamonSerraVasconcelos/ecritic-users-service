package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressesBoundary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserAddressesUseCase {

    private final FindUserAddressesBoundary findUserAddressesBoundary;

    public List<Address> execute(UUID userId) {
        log.info("Finding addresses for user with id: [{}]", userId);

        try {
            return findUserAddressesBoundary.execute(userId);
        } catch (Exception ex) {
            log.error("Error finding addresses for user with id: [{}]", userId);
            throw ex;
        }
    }
}
