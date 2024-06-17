package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetUseCase {

    private final FindUserByIdBoundary findUserByIdBoundary;

    private final SaveUserBoundary saveUserBoundary;

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    private final BCryptPasswordEncoder bcrypt;

    public void execute(UUID userId, String passwordResetHash, String newPassword, String passwordConfirmation) {
        log.info("Resetting password for user with id [{}]", userId);

        try {
            if (!newPassword.equals(passwordConfirmation)) {
                log.warn("Passwords do not match for user with id [{}]", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_14);
            }

            Optional<User> optionalUser = findUserByIdBoundary.execute(userId);

            if (optionalUser.isEmpty()) {
                log.warn("User with id [{}] not found", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
            }

            User user = optionalUser.get();

            boolean isPasswordResetHashValid = bcrypt.matches(passwordResetHash, user.getPasswordResetHash());

            if (!isPasswordResetHashValid) {
                log.warn("Password reset hash is not valid for user with id [{}]", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
            }

            if (LocalDateTime.now().isAfter(user.getPasswordResetDate())) {
                log.warn("Password reset hash has expired for user with id [{}]", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
            }

            boolean isPasswordDuplicated = bcrypt.matches(newPassword, user.getPassword());

            if (isPasswordDuplicated) {
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_13);
            }

            String encodedPassword = bcrypt.encode(newPassword);
            user.setPassword(encodedPassword);
            user.setPasswordResetHash(null);
            user.setPasswordResetDate(null);

            saveUserBoundary.execute(user);

            sendEmailNotificationUseCase.execute(user.getId(), user.getEmail(), NotificationContentEnum.PASSWORD_RESET, null);
        } catch (DefaultException ex) {
            log.error("Failed to reset password for user with id: [{}]. Exception: [{}]", userId, ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to reset password for user with id: [{}]", userId, ex);
            throw ex;
        }
    }
}
