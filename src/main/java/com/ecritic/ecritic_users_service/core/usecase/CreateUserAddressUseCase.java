package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserAddressBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserAddressUseCase {

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final CreateAddressUseCase createAddressUseCase;

    private final SaveUserAddressBoundary saveUserAddressBoundary;

    private final SetDefaultUserAddressUseCase setDefaultUserAddressUseCase;

    public Address execute(UUID userId, Address address) {
        log.info("Adding address for user with id: [{}}", userId);

        try {
            Optional<User> optionalUser = findUserByIdBoundary.execute(userId);

            if (optionalUser.isEmpty()) {
                throw new EntityNotFoundException("User not found");
            }

            Address savedAddress = createAddressUseCase.execute(address);

            saveUserAddressBoundary.execute(userId, savedAddress.getId());
            savedAddress.setDefault(address.isDefault());

            if (savedAddress.isDefault()) {
                setDefaultUserAddressUseCase.execute(userId, savedAddress.getId());
            }

            return savedAddress;
        } catch (Exception ex) {
            log.error("Error while adding address for user with id: [{}}", userId, ex);
            throw ex;
        }
    }
}
