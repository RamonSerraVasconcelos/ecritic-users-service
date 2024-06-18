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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByIdGatewayTest {

    @InjectMocks
    private FindUserByIdGateway findUserByIdGateway;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void givenExecution_whenUserIsFound_thenReturnUser() {
        User user = UserFixture.load();
        UserEntity userEntity = UserEntityFixture.load();

        when(userEntityRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.userEntityToUser(any())).thenReturn(user);

        Optional<User> foundUser = findUserByIdGateway.execute(user.getId());

        verify(userEntityRepository).findById(any());

        assertThat(foundUser).isPresent().get().usingRecursiveComparison().isEqualTo(user);
    }
}