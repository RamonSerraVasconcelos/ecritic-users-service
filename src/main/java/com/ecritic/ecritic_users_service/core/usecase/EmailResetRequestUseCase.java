package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.EntityConflictException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailResetRequestUseCase {

    private final FindUserByIdUseCase findUserByIdUseCase;

    private final FindUserByEmailBoundary findUserByEmailBoundary;

    private final SaveUserBoundary updateUserBoundary;

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    private final BCryptPasswordEncoder bcrypt;

    public void execute(UUID userId, String newEmail) {
        log.info("Requesting email reset for user with id: [{}]", userId);

        try {
            User user = findUserByIdUseCase.execute(userId);

            User isUserDuplicated = findUserByEmailBoundary.execute(newEmail);

            if (nonNull(isUserDuplicated)) {
                throw new EntityConflictException(ErrorResponseCode.ECRITICUSERS_07);
            }

            if (user.getEmail().equals(newEmail)) {
                log.info("User with id: [{}] already has the new email: [{}]", user.getId(), newEmail);
                throw new EntityConflictException(ErrorResponseCode.ECRITICUSERS_12);
            }

            String emailResetHash = UUID.randomUUID().toString();
            String encryptedHash = bcrypt.encode(emailResetHash);

            user.setEmailResetHash(encryptedHash);
            user.setEmailResetDate(LocalDateTime.now().plusMinutes(15));
            user.setNewEmailReset(newEmail);

            updateUserBoundary.execute(user);

            Map<String, String> notificationBodyVariables = new HashMap<>();
            notificationBodyVariables.put("userId", user.getId().toString());
            notificationBodyVariables.put("emailResetHash", emailResetHash);

            sendEmailNotificationUseCase.execute(user.getId(), user.getNewEmailReset(), NotificationContentEnum.EMAIL_RESET_REQUEST, notificationBodyVariables);
        } catch (DefaultException ex) {
            log.error("Error while requesting email reset for user with id: [{}], Exception: [{}]", userId, ex.getErrorResponse());
            throw ex;
        } catch (Exception ex) {
            log.error("Error while requesting email reset for user with id: [{}]", userId, ex);
            throw ex;
        }
    }
}
