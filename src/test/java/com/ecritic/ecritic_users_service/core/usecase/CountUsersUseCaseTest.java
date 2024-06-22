package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFilterFixture;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CacheUsersCountBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.CountUsersBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCachedUsersCountBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountUsersUseCaseTest {

    @InjectMocks
    private CountUsersUseCase countUsersUseCase;

    @Mock
    private FindCachedUsersCountBoundary findCachedUsersCountBoundary;

    @Mock
    private CountUsersBoundary countUsersBoundary;

    @Mock
    private CacheUsersCountBoundary cacheUsersCountBoundary;

    @Test
    void givenCacheableFilter_whenResultIsCached_thenReturnResultFromCache() {
        UserFilter userFilter = UserFilterFixture.load();

        when(findCachedUsersCountBoundary.execute()).thenReturn(10L);

        Long result = countUsersUseCase.execute(userFilter);

        verifyNoInteractions(countUsersBoundary, cacheUsersCountBoundary);
        assertThat(result).isEqualTo(10L);
    }

    @Test
    void givenCacheableFilter_whenResultIsNotCached_thenReturnResultFromDb() {
        UserFilter userFilter = UserFilterFixture.load();

        when(findCachedUsersCountBoundary.execute()).thenReturn(null);
        when(countUsersBoundary.execute(userFilter)).thenReturn(10L);

        Long result = countUsersUseCase.execute(userFilter);

        verify(findCachedUsersCountBoundary).execute();
        verify(countUsersBoundary).execute(userFilter);
        verify(cacheUsersCountBoundary).execute(10L);

        assertThat(result).isEqualTo(10L);
    }

    @Test
    void givenNotCacheableFilter_whenResultIsNotCached_thenReturnResultFromDb() {
        UserFilter userFilter = UserFilterFixture.loadWithParams();

        when(countUsersBoundary.execute(userFilter)).thenReturn(10L);

        Long result = countUsersUseCase.execute(userFilter);

        verify(countUsersBoundary).execute(userFilter);
        verifyNoInteractions(findCachedUsersCountBoundary, cacheUsersCountBoundary);

        assertThat(result).isEqualTo(10L);
    }
}