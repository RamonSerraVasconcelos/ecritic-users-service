package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserAddressByIdBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserAddresUseCase {

    private final FindUserAddressByIdBoundary findUserAddressByIdBoundary;

    public Address execute(UUID userId, UUID addressId) {
        log.info("Finding address for user with id: [{}] and addressId: [{}]", userId, addressId);

        try {
            Optional<Address> optionalAddress = findUserAddressByIdBoundary.execute(userId, addressId);

            if (optionalAddress.isEmpty()) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_10);
            }

            return optionalAddress.get();
        } catch (Exception ex) {
            log.error("Error finding address for user with id: [{}] and addressId: [{}]", userId, addressId, ex);
            throw ex;
        }
    }
}
