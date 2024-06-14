package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.NotificationContentEnum;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindUserByEmailBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveUserBoundary;
import com.ecritic.ecritic_users_service.exception.DefaultException;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetRequestUseCase {

    private final FindUserByEmailBoundary findUserByEmailBoundary;

    private final SaveUserBoundary saveUserBoundary;

    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    private final BCryptPasswordEncoder bcrypt;

    public void execute(String email) {
        log.info("Starting password reset flow for user with email: [{}]", email);

        try {
            User user = findUserByEmailBoundary.execute(email);

            if (isNull(user)) {
                throw new EntityNotFoundException(ErrorResponseCode.ECRITICUSERS_09);
            }

            String passwordResetHash = UUID.randomUUID().toString();
            String encryptedHash = bcrypt.encode(passwordResetHash);

            user.setPasswordResetHash(encryptedHash);
            user.setPasswordResetDate(LocalDateTime.now().plusMinutes(5));

            saveUserBoundary.execute(user);

            Map<String, String> notificationBodyVariables = new HashMap<>();
            notificationBodyVariables.put("userId", user.getId().toString());
            notificationBodyVariables.put("passwordResetHash", passwordResetHash);

            sendEmailNotificationUseCase.execute(user.getId(), user.getEmail(), NotificationContentEnum.PASSWORD_RESET_REQUEST, notificationBodyVariables);
        } catch (DefaultException ex) {
            log.error("Error while requesting password reset for user with email: [{}], Exception: [{}]", email, ex.getErrorResponse());
        } catch (Exception ex) {
            log.error("Error while requesting password reset for user with email: [{}]", email, ex);
        }
    }
}
