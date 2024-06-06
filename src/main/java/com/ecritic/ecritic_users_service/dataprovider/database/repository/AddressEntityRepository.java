package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressEntityRepository extends JpaRepository<AddressEntity, UUID>, AddressEntityCustomRepository {
}
