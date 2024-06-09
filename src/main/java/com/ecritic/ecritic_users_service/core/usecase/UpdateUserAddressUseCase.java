package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserAddressUseCase {

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final UpdateAddressUseCase updateAddressUseCase;

    private final SetDefaultUserAddressUseCase setDefaultUserAddressUseCase;

    public Address execute(UUID userId, UUID addressId, Address address) {
        log.info("Updating address for user with id: [{}] and addressId: [{}]", userId, addressId);

        try {
            Optional<User> optionalUser = findUserByIdBoundary.execute(userId);

            if (optionalUser.isEmpty()) {
                throw new EntityNotFoundException("User not found");
            }

            Address updatedAddress = updateAddressUseCase.execute(addressId, address);
            updatedAddress.setDefault(address.isDefault());

            if (address.isDefault()) {
                setDefaultUserAddressUseCase.execute(userId, updatedAddress.getId());
            }

            return updatedAddress;
        } catch (Exception ex) {
            log.error("Error while updating address for user with id: [{}] and addressId: [{}]", userId, addressId, ex);
            throw ex;
        }
    }
}
