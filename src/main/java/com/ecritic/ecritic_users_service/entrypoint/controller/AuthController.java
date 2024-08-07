package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByEmailUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByIdUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpsertExternalUserUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AuthorizationInfoMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import com.ecritic.ecritic_users_service.entrypoint.dto.ExternalUserRequestDto;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Validator validator;

    private final FindUserByEmailUseCase findUserByEmailUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

    private final UpsertExternalUserUseCase upsertExternalUserUseCase;

    private final AuthorizationInfoMapper authorizationInfoMapper;

    @GetMapping("/info")
    public ResponseEntity<AuthorizationInfo> getAuthorizationInfo(@RequestParam(name = "email", required = false) String email,
                                                                  @RequestParam(name = "userId", required = false) UUID userId) {

        if (email == null && userId == null) {
            throw new ResourceViolationException(ErrorResponseCode.ECRITICUSERS_01.getMessage());
        }

        User user;

        if (userId != null) {
            user = findUserByIdUseCase.execute(userId);
        } else {
            user = findUserByEmailUseCase.execute(email);
        }

        return ResponseEntity.status(HttpStatus.OK).body(authorizationInfoMapper.userToAuthorizationInfo(user));
    }

    @PutMapping("/users/external")
    public ResponseEntity<AuthorizationInfo> upsertExternalUser(@RequestBody ExternalUserRequestDto externalUserRequestDto) {
        Set<ConstraintViolation<ExternalUserRequestDto>> violations = validator.validate(externalUserRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        User user = upsertExternalUserUseCase.execute(externalUserRequestDto.getEmail(), externalUserRequestDto.getName());

        return ResponseEntity.status(HttpStatus.OK).body(authorizationInfoMapper.userToAuthorizationInfo(user));
    }
}
