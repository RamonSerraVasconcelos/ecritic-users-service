package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByEmailUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByIdUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AuthorizationInfoMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUserByEmailUseCase findUserByEmailUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

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
}
