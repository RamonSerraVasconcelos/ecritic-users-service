package com.ecritic.ecritic_users_service.core.usecase.boundary;

import com.ecritic.ecritic_users_service.core.model.Address;

public interface SaveAddressBoundary {

    Address execute(Address address);
}
