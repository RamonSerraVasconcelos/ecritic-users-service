package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;

import java.util.List;
import java.util.UUID;

public interface AddressEntityCustomRepository {

    void saveUserAddress(UUID userId, UUID addressId);

    void unsetUserDefaultAddress(UUID userId);

    void setUserDefaultAddress(UUID userId, UUID addressId);

    List<AddressEntity> findUserAddressesByUserId(UUID userId);

    AddressEntity findUserAddressByIds(UUID userId, UUID addressId);
}
