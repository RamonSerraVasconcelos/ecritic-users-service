package com.ecritic.ecritic_users_service.dataprovider.database.repository;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID>, UserEntityCustomRepository {

    UserEntity findByEmail(String email);
}
