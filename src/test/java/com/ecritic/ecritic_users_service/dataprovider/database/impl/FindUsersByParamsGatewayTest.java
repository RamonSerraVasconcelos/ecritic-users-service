package com.ecritic.ecritic_users_service.dataprovider.database.impl;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserEntityMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUsersByParamsGatewayTest {

    @InjectMocks
    private FindUsersByParamsGateway findUsersByParamsGateway;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void givenExecution_thenSearch_andReturnUsers() {
        UserFilter userFilter = UserFilter.builder().build();
        UserEntity userEntity = UserEntity.builder().build();
        User user = UserFixture.load();

        when(userEntityRepository.findUserListByParams(any())).thenReturn(List.of(userEntity, userEntity));
        when(userEntityMapper.userEntityToUser(any())).thenReturn(user, user);

        List<User> users = findUsersByParamsGateway.execute(userFilter);

        verify(userEntityRepository).findUserListByParams(any());
        verify(userEntityMapper, times(2)).userEntityToUser(any());

        assertThat(users).isNotEmpty().hasSize(2).usingRecursiveComparison().isEqualTo(List.of(user, user));
    }
}