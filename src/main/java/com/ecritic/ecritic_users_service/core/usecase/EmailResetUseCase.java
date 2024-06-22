package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.BusinessViolationException;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailResetUseCase {

    private final FindUserByIdUseCase findUserByIdUseCase;

    private final SaveUserBoundary saveUserBoundary;

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void execute(UUID userId, String emailResetHash) {
        log.info("Changing email for user with id: [{}]", userId);

        try {
            User user = findUserByIdUseCase.execute(userId);

            boolean isEmailResetHashValid = bCryptPasswordEncoder.matches(emailResetHash, user.getEmailResetHash());

            if (!isEmailResetHashValid) {
                log.warn("Invalid email change token: [{}] for user with id: [{}]", emailResetHash, userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
            }

            if (LocalDateTime.now().isAfter(user.getEmailResetDate())) {
                log.warn("User with id: [{}] tried to change email after token expiration", userId);
                throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
            }

            user.setEmail(user.getNewEmailReset());
            user.setEmailResetHash(null);
            user.setEmailResetDate(null);

            saveUserBoundary.execute(user);

            log.info("Finished replacing email: [{}] for user with id: [{}]", user.getEmail(), user.getId());

            sendEmailNotificationUseCase.execute(user.getId(), user.getEmail(), NotificationContentEnum.EMAIL_RESET, null);
        } catch (DefaultException ex) {
            log.error("Error when changing email for user with id: [{}]. Exception: [{}]", userId, ex.getErrorResponse());
            throw ex;
        } catch (Exception e) {
            log.error("Error when changing email for user with id: [{}]", userId, e);
            throw new BusinessViolationException(ErrorResponseCode.ECRITICUSERS_01);
        }
    }
}
