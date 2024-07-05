package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUserCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.InvalidateUsersCacheBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.PostStatusUpdateMessageBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.UpdateBanListBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.UpdateUserStatusBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserStatusUseCaseTest {


    @InjectMocks
    private UpdateUserStatusUseCase updateUserStatusUseCase;

    @Mock
    private FindUserByIdBoundary findUserByIdBoundary;

    @Mock
    private UpdateUserStatusBoundary updateUserStatusBoundary;

    @Mock
    private UpdateBanListBoundary updateBanListBoundary;

    @Mock
    private PostStatusUpdateMessageBoundary postStatusUpdateMessageBoundary;

    @Mock
    private InvalidateUsersCacheBoundary invalidateUsersCacheBoundary;

    @Mock
    private InvalidateUserCacheBoundary invalidateUserCacheBoundary;

    @Captor
    private ArgumentCaptor<Boolean> activeStatusCaptor;

    private static final UUID USER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");

    private static final String MOTIVE = "Test scenarios";

    @ParameterizedTest
    @EnumSource(value = BanActionEnum.class, names = {"BAN", "UNBAN"})
    void givenExecutionWithValidParameters_thenUpdateUserStatus(BanActionEnum action) {
        User user = UserFixture.load();
        user.setActive(action == BanActionEnum.BAN);

        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.of(user));

        updateUserStatusUseCase.execute(USER_ID, MOTIVE, action);

        verify(findUserByIdBoundary).execute(USER_ID);
        verify(updateUserStatusBoundary).execute(any(UUID.class), activeStatusCaptor.capture());
        verify(updateBanListBoundary).execute(USER_ID, MOTIVE, action);
        verify(postStatusUpdateMessageBoundary).execute(any(UUID.class), activeStatusCaptor.capture());
        verify(invalidateUsersCacheBoundary).execute();
        verify(invalidateUserCacheBoundary).execute(USER_ID);

        assertThat(activeStatusCaptor.getValue()).isEqualTo(action != BanActionEnum.BAN);
    }

    @Test
    void givenExecution_whenUserIsNotFound_thenThrowEntityNotFoundException() {
        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> updateUserStatusUseCase.execute(USER_ID, MOTIVE, BanActionEnum.BAN));

        verify(findUserByIdBoundary).execute(USER_ID);
        verifyNoInteractions(updateUserStatusBoundary);
        verifyNoInteractions(updateBanListBoundary);
        verifyNoInteractions(postStatusUpdateMessageBoundary);
        verifyNoInteractions(invalidateUsersCacheBoundary);
        verifyNoInteractions(invalidateUserCacheBoundary);

        assertThat(ex.getErrorResponse().getCode()).isEqualTo(ErrorResponseCode.ECRITICUSERS_09.getCode());
    }

    @ParameterizedTest
    @EnumSource(value = BanActionEnum.class, names = {"BAN", "UNBAN"})
    void givenExecution_whenUserStatusIsAlreadyTheSame_thenDoNothing(BanActionEnum action) {
        User user = UserFixture.load();
        user.setActive(action != BanActionEnum.BAN);

        when(findUserByIdBoundary.execute(USER_ID)).thenReturn(Optional.of(user));

        updateUserStatusUseCase.execute(USER_ID, MOTIVE, action);

        verify(findUserByIdBoundary).execute(USER_ID);
        verifyNoInteractions(updateUserStatusBoundary);
        verifyNoInteractions(updateBanListBoundary);
        verifyNoInteractions(postStatusUpdateMessageBoundary);
        verifyNoInteractions(invalidateUsersCacheBoundary);
        verifyNoInteractions(invalidateUserCacheBoundary);
    }
}