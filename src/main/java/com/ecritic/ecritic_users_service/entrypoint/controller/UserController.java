package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserUseCase;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.mapper.UserDtoMapper;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Validator validator;

    private final CreateUserUseCase createUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    private final UserDtoMapper userDtoMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        User user = userDtoMapper.userRequestDtoToUser(userRequestDto);

        User createdUser = createUserUseCase.execute(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.userToUserResponseDto(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> editUser(HttpServletRequest request, @PathVariable String id, @RequestBody UserRequestDto userRequestDto) {
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        if (!violations.isEmpty()) {
            violations.forEach(violation -> {
                if (!violation.getPropertyPath().toString().equals("email") && !violation.getPropertyPath().toString().equals("password")) {
                    throw new ResourceViolationException(violation);
                }
            });
        }

        String userId = request.getAttribute("userId").toString();

        if (!Objects.equals(userId, id)) {
            throw new ResourceViolationException("Invalid request data");
        }

        User user = updateUserUseCase.execute(UUID.fromString(id), userDtoMapper.userRequestDtoToUser(userRequestDto));

        return ResponseEntity.ok().body(userDtoMapper.userToUserResponseDto(user));
    }
}
