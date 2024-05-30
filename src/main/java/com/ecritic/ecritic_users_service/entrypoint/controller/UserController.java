package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserUseCase;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.mapper.UserDtoMapper;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Validator validator;

    private final CreateUserUseCase createUserUseCase;

    private final UserDtoMapper userDtoMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        User user = userDtoMapper.userRequestDtoToUser(userRequestDto);

        User createdUser = createUserUseCase.execute(user);

        return ResponseEntity.ok().body(userDtoMapper.userToUserResponseDto(createdUser));
    }
}
