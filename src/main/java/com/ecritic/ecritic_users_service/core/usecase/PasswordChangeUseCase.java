package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeUseCase {

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final SaveUserBoundary saveUserBoundary;

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    private final BCryptPasswordEncoder bcrypt;

    public void execute(UUID userId, String currentPassword, String newPassword, String newPasswordConfirmation) {
        log.info("Changing password for user with id: [{}]", userId);

        try {
            if (!newPassword.equals(newPasswordConfirmation)) {
                log.warn("Passwords do not match for user with id [{}]", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_14);
            }

            Optional<User> optionalUser = findUserByIdBoundary.execute(userId);

            if (optionalUser.isEmpty()) {
                log.warn("User with id [{}] not found", userId);
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
            }

            User user = optionalUser.get();

            if (nonNull(user.getPassword())) {
                boolean isCurrentPasswordValid = bcrypt.matches(currentPassword, user.getPassword());

                if (!isCurrentPasswordValid) {
                    log.warn("Password is not valid for user with id [{}]", userId);
                    throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_15);
                }

                boolean isPasswordDuplicated = bcrypt.matches(newPassword, user.getPassword());

                if (isPasswordDuplicated) {
                    log.warn("Password is duplicated for user with id [{}]", userId);
                    throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_13);
                }
            }

            user.setPassword(bcrypt.encode(newPassword));

            saveUserBoundary.execute(user);

            sendEmailNotificationUseCase.execute(user.getId(), user.getEmail(), NotificationContentEnum.PASSWORD_CHANGE, null);
        } catch (DefaultException ex) {
            log.error("Error changing password for user with id: [{}]. Exception: [{}]", userId, ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error changing password for user with id: [{}]", userId, ex);
            throw ex;
        }
    }
}
