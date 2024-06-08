package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Address;

import java.util.Optional;
import java.util.UUID;

public interface FindAddressByIdBoundary {

    Optional<Address> execute(UUID addressId);
}
