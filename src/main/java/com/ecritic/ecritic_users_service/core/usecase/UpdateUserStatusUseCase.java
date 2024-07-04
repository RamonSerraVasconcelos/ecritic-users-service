package com.ecritic.ecritic_users_service.core.usecase;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserStatusUseCase {

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final UpdateUserStatusBoundary updateUserStatusBoundary;

    private final UpdateBanListBoundary updateBanListBoundary;

    private final PostStatusUpdateMessageBoundary postStatusUpdateMessageBoundary;

    private final InvalidateUsersCacheBoundary invalidateUsersCacheBoundary;

    private final InvalidateUserCacheBoundary invalidateUserCacheBoundary;

    public void execute(UUID id, String motive, BanActionEnum action) {
        try {
            log.info("Updating user [{}] status with action: [{}]", id, action.name());

            Optional<User> optionalUser = findUserByIdBoundary.execute(id);

            if (optionalUser.isEmpty()) {
                log.warn("User [{}] not found", id);
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
            }

            boolean active = action != BanActionEnum.BAN;

            if(optionalUser.get().isActive() == active) {
                log.warn("User [{}] active status is already: [{}]", id, active);
                return;
            }

            updateUserStatusBoundary.execute(id, active);
            updateBanListBoundary.execute(id, motive, action);
            postStatusUpdateMessageBoundary.execute(id, active);

            invalidateUsersCacheBoundary.execute();
            invalidateUserCacheBoundary.execute(id);
        } catch (Exception ex) {
            log.error("Error updating status for userId: [{}]", id, ex);
            throw ex;
        }
    }
}
