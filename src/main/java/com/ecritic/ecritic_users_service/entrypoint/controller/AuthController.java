package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByEmailUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AuthorizationInfoMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUserByEmailUseCase findUserByEmailUseCase;

    private final AuthorizationInfoMapper authorizationInfoMapper;

    @GetMapping("/info")
    public ResponseEntity<AuthorizationInfo> getAuthorizationInfo(@RequestParam("email") String email) {
        User user = findUserByEmailUseCase.execute(email);

        return ResponseEntity.status(HttpStatus.OK).body(authorizationInfoMapper.userToAuthorizationInfo(user));
    }
}
