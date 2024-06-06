package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import java.util.UUID;

public interface AddressEntityCustomRepository {

    void saveUserAddress(UUID userId, UUID addressId);

    void unsetUserDefaultAddress(UUID userId);

    void setUserDefaultAddress(UUID userId, UUID addressId);
}
