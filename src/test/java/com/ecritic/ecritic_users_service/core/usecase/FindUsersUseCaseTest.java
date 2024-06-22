package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFilterFixture;
import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUsersByParamsBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUsersUseCaseTest {

    @InjectMocks
    private FindUsersUseCase findUsersUseCase;

    @Mock
    private FindCachedUsersBoundary findCachedUsersBoundary;

    @Mock
    private FindUsersByParamsBoundary findUsersByParamsBoundary;

    @Mock
    private CountUsersUseCase countUsersUseCase;

    @Mock
    private CacheUsersBoundary cacheUsersBoundary;

    @Test
    void givenCacheableFilter_whenUsersAreCached_thenReturnUsersFromCache() {
        UserFilter userFilter = UserFilterFixture.load();

        when(findCachedUsersBoundary.execute(userFilter.getPageable())).thenReturn(List.of(UserFixture.load(), UserFixture.load(), UserFixture.load()));
        when(countUsersUseCase.execute(userFilter)).thenReturn(3L);

        Page<User> users = findUsersUseCase.execute(userFilter);

        verify(findCachedUsersBoundary).execute(userFilter.getPageable());
        verify(countUsersUseCase).execute(userFilter);
        verifyNoInteractions(findUsersByParamsBoundary, cacheUsersBoundary);

        assertThat(users).isNotEmpty().hasSize(3);
    }

    @Test
    void givenCacheableFilter_whenUsersAreNotCached_thenReturnUsersFromDb() {
        UserFilter userFilter = UserFilterFixture.load();

        when(findCachedUsersBoundary.execute(userFilter.getPageable())).thenReturn(List.of());
        when(findUsersByParamsBoundary.execute(userFilter)).thenReturn(List.of(UserFixture.load(), UserFixture.load(), UserFixture.load()));
        when(countUsersUseCase.execute(userFilter)).thenReturn(3L);

        Page<User> users = findUsersUseCase.execute(userFilter);

        verify(findCachedUsersBoundary).execute(userFilter.getPageable());
        verify(findUsersByParamsBoundary).execute(userFilter);
        verify(countUsersUseCase).execute(userFilter);
        verify(cacheUsersBoundary).execute(users);

        assertThat(users).isNotEmpty().hasSize(3);
    }

    @Test
    void givenNotCacheableFilter_thenReturnUsersFromDb() {
        UserFilter userFilter = UserFilterFixture.loadWithParams();

        when(findUsersByParamsBoundary.execute(userFilter)).thenReturn(List.of(UserFixture.load(), UserFixture.load(), UserFixture.load()));
        when(countUsersUseCase.execute(userFilter)).thenReturn(3L);

        Page<User> users = findUsersUseCase.execute(userFilter);

        verify(findUsersByParamsBoundary).execute(userFilter);
        verify(countUsersUseCase).execute(userFilter);
        verifyNoInteractions(findCachedUsersBoundary, cacheUsersBoundary);

        assertThat(users).isNotEmpty().hasSize(3);
    }
}