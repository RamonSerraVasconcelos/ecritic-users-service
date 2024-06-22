package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.UserEntityFixture;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveUserGatewayTest {

    @InjectMocks
    private SaveUserGateway saveUserGateway;

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void givenExecution_thenSave_andReturnUser() {
        User user = UserFixture.load();
        UserEntity userEntity = UserEntityFixture.load();

        when(userRepository.save(any())).thenReturn(userEntity);
        when(userEntityMapper.userToUserEntity(any())).thenReturn(userEntity);
        when(userEntityMapper.userEntityToUser(any())).thenReturn(user);

        User savedUser = saveUserGateway.execute(user);

        verify(userEntityMapper).userToUserEntity(any());
        verify(userEntityMapper).userEntityToUser(any());
        verify(userRepository).save(any());

        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }
}