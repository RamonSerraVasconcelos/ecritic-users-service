package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Address;

import java.util.List;
import java.util.UUID;

public interface FindUserAddressesBoundary {

    List<Address> execute(UUID userId);
}
