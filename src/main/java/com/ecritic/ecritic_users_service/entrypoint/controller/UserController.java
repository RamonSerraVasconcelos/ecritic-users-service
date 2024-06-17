package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserAddressUseCase;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserUseCase;
import com.ecritic.ecritic_users_service.core.usecase.EmailResetRequestUseCase;
import com.ecritic.ecritic_users_service.core.usecase.EmailResetUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserAddresUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserAddressesUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByIdUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUsersUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordChangeUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordResetRequestUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordResetUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserAddressUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserFilterMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;
import com.ecritic.ecritic_users_service.entrypoint.dto.ChangePasswordDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.Metadata;
import com.ecritic.ecritic_users_service.entrypoint.dto.PageableUserResponse;
import com.ecritic.ecritic_users_service.entrypoint.dto.PasswordResetDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AddressDtoMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.UserDtoMapper;
import com.ecritic.ecritic_users_service.exception.ResourceViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Validator validator;

    private final CreateUserUseCase createUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    private final FindUsersUseCase findUsersUseCase;

    private final FindUserByIdUseCase findUserByIdUseCase;

    private final CreateUserAddressUseCase createUserAddressUseCase;

    private final UpdateUserAddressUseCase updateUserAddressUseCase;

    private final FindUserAddressesUseCase findUserAddressesUseCase;

    private final FindUserAddresUseCase findUserAddresUseCase;

    private final EmailResetRequestUseCase emailResetRequestUseCase;

    private final EmailResetUseCase emailResetUseCase;

    private final PasswordResetRequestUseCase passwordResetRequestUseCase;

    private final PasswordResetUseCase passwordResetUseCase;

    private final PasswordChangeUseCase passwordChangeUseCase;

    private final AuthorizationTokenDataMapper authorizationTokenDataMapper;

    private final UserDtoMapper userDtoMapper;

    private final UserFilterMapper userFilterMapper;

    private final AddressDtoMapper addressDtoMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        User user = userDtoMapper.userRequestDtoToUser(userRequestDto);

        User createdUser = createUserUseCase.execute(user, userRequestDto.getPasswordConfirmation());

        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.userToUserResponseDto(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> editUser(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable UUID id,
                                                    @RequestBody UserRequestDto userRequestDto) {

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        if (!violations.isEmpty()) {
            violations.forEach(violation -> {
                if (!violation.getPropertyPath().toString().equals("email") && !violation.getPropertyPath().toString().equals("password")) {
                    throw new ResourceViolationException(violation);
                }
            });
        }

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        if (!authorizationTokenData.getUserId().equals(id)) {
            throw new ResourceViolationException("Invalid request data");
        }

        User user = updateUserUseCase.execute(id, userDtoMapper.userRequestDtoToUser(userRequestDto));

        return ResponseEntity.ok().body(userDtoMapper.userToUserResponseDto(user));
    }

    @GetMapping
    public ResponseEntity<PageableUserResponse> findUsers(Pageable pageable,
                                                          @RequestParam(name = "active", required = false, defaultValue = "true") boolean active,
                                                          @RequestParam(name = "userIds", required = false) List<String> userIds,
                                                          @RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "email", required = false) String email) {

        UserFilter userFilter = userFilterMapper.map(pageable, active, name, email, userIds);

        Page<User> users = findUsersUseCase.execute(userFilter);

        List<UserResponseDto> usersResponseDto = users.getContent()
                .stream()
                .map(userDtoMapper::userToUserResponseDto)
                .toList();

        PageableUserResponse pageableUserResponse = PageableUserResponse.builder()
                .data(usersResponseDto)
                .metadata(Metadata.builder()
                        .page(pageable.getPageNumber())
                        .nextPage(pageable.getPageNumber() + 1)
                        .size(pageable.getPageSize())
                        .total(users.getTotalElements())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pageableUserResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@RequestHeader("Authorization") String authorization, @PathVariable UUID id) {
        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);

        if (!authorizationTokenData.getUserId().equals(id)) {
            throw new ResourceViolationException("Invalid request data");
        }

        User user = findUserByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.userToUserResponseDto(user));
    }

    @PostMapping("/{userId}/address")
    public ResponseEntity<AddressResponseDto> createUserAddress(@RequestHeader("Authorization") String authorization,
                                                                @PathVariable("userId") UUID userId,
                                                                @RequestBody AddressRequestDto addressRequestDto) {

        Set<ConstraintViolation<AddressRequestDto>> violations = validator.validate(addressRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        if (!authorizationTokenData.getUserId().equals(userId)) {
            throw new ResourceViolationException("Invalid request data");
        }

        Address address = addressDtoMapper.addressRequestDtoToAddress(addressRequestDto);

        Address savedAddress = createUserAddressUseCase.execute(userId, address);

        AddressResponseDto addressResponseDto = addressDtoMapper.addressToAddressResponseDto(savedAddress);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponseDto);
    }

    @PutMapping("/{userId}/address/{addressId}")
    public ResponseEntity<AddressResponseDto> updateUserAddress(@RequestHeader("Authorization") String authorization,
                                                                @PathVariable("userId") UUID userId,
                                                                @PathVariable("addressId") UUID addressId,
                                                                @RequestBody AddressRequestDto addressRequestDto) {

        Set<ConstraintViolation<AddressRequestDto>> violations = validator.validate(addressRequestDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        if (!authorizationTokenData.getUserId().equals(userId)) {
            throw new ResourceViolationException("Invalid request data");
        }

        Address address = addressDtoMapper.addressRequestDtoToAddress(addressRequestDto);

        Address updatedAddress = updateUserAddressUseCase.execute(userId, addressId, address);

        AddressResponseDto addressResponseDto = addressDtoMapper.addressToAddressResponseDto(updatedAddress);

        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDto);
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<AddressResponseDto>> findUserAddresses(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable("userId") UUID userId) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        if (!authorizationTokenData.getUserId().equals(userId)) {
            throw new ResourceViolationException("Invalid request data");
        }

        List<Address> addresses = findUserAddressesUseCase.execute(userId);

        List<AddressResponseDto> addressesResponseDto = addresses
                .stream()
                .map(addressDtoMapper::addressToAddressResponseDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(addressesResponseDto);
    }

    @GetMapping("/{userId}/address/{addressId}")
    public ResponseEntity<AddressResponseDto> getUserAddress(@RequestHeader("Authorization") String authorization,
                                                             @PathVariable("userId") UUID userId,
                                                             @PathVariable("addressId") UUID addressId) {

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);
        if (!authorizationTokenData.getUserId().equals(userId)) {
            throw new ResourceViolationException("Invalid request data");
        }

        Address address = findUserAddresUseCase.execute(userId, addressId);

        AddressResponseDto addressResponseDto = addressDtoMapper.addressToAddressResponseDto(address);

        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDto);
    }

    @PostMapping("/request-email-change")
    public ResponseEntity<Void> requestEmailChange(@RequestHeader("Authorization") String authorization, @RequestBody UserRequestDto userDto) {
        if (isNull(userDto.getEmail())) {
            throw new ResourceViolationException("Email is required");
        }

        AuthorizationTokenData authorizationTokenData = authorizationTokenDataMapper.map(authorization);

        emailResetRequestUseCase.execute(authorizationTokenData.getUserId(), userDto.getEmail());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{userId}/change-email")
    public ResponseEntity<Void> emailReset(@PathVariable("userId") UUID userId, @RequestParam("token") String emailResetHash) {
        emailResetUseCase.execute(userId, emailResetHash);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<Void> passwordResetRequest(@RequestBody UserRequestDto userRequestDto) {
        if (isNull(userRequestDto.getEmail())) {
            throw new ResourceViolationException("Email is required");
        }

        passwordResetRequestUseCase.execute(userRequestDto.getEmail());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{userId}/reset-password")
    public ResponseEntity<Void> passwordReset(@PathVariable("userId") UUID userId, @RequestBody PasswordResetDto passwordResetData) {
        Set<ConstraintViolation<PasswordResetDto>> violations = validator.validate(passwordResetData);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        passwordResetUseCase.execute(userId,
                passwordResetData.getPasswordResetHash(),
                passwordResetData.getPassword(),
                passwordResetData.getPasswordConfirmation());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String authorization,
                                               @PathVariable("userId") UUID userId,
                                               @RequestBody ChangePasswordDto changePasswordDto) {

        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        if (!violations.isEmpty()) {
            throw new ResourceViolationException(violations);
        }

        passwordChangeUseCase.execute(userId, changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword(), changePasswordDto.getPasswordConfirmation());

        return ResponseEntity.noContent().build();
    }
}

