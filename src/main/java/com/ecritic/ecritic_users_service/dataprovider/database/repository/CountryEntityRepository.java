package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryEntityRepository extends JpaRepository<CountryEntity, Long> {
}
