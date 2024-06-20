package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {

    private UserEntityMapper userEntityMapper;

    @BeforeEach
    public void setUp() {
        userEntityMapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Test
    void givenUser_thenReturnUserEntity() {
        User user = UserFixture.load();

        UserEntity userEntity = userEntityMapper.userToUserEntity(user);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isEqualTo(user.getId());
        assertThat(userEntity.getName()).isEqualTo(user.getName());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(userEntity.getPassword()).isEqualTo(user.getPassword());
        assertThat(userEntity.getDescription()).isEqualTo(user.getDescription());
        assertThat(userEntity.getPhone()).isEqualTo(user.getPhone());
        assertThat(userEntity.isActive()).isEqualTo(user.isActive());
        assertThat(userEntity.getRole().name()).isEqualTo(user.getRole().name());
        assertThat(userEntity.getCountry().getId()).isEqualTo(user.getCountry().getId());
        assertThat(userEntity.getCountry().getName()).isEqualTo(user.getCountry().getName());
    }

    @Test
    void givenUserEntity_thenReturnUser() {
        UserEntity userEntity = UserEntityFixture.load();

        User user = userEntityMapper.userEntityToUser(userEntity);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getName()).isEqualTo(userEntity.getName());
        assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(user.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(user.getDescription()).isEqualTo(userEntity.getDescription());
        assertThat(user.getPhone()).isEqualTo(userEntity.getPhone());
        assertThat(user.isActive()).isEqualTo(userEntity.isActive());
        assertThat(user.getRole().name()).isEqualTo(userEntity.getRole().name());
        assertThat(user.getCountry().getId()).isEqualTo(userEntity.getCountry().getId());
        assertThat(user.getCountry().getName()).isEqualTo(userEntity.getCountry().getName());
    }
}