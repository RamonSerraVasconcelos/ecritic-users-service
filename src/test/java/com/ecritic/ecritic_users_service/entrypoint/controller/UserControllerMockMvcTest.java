package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.enums.BanActionEnum;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserAddressUseCase;
import com.ecritic.ecritic_users_service.core.usecase.CreateUserUseCase;
import com.ecritic.ecritic_users_service.core.usecase.EmailResetRequestUseCase;
import com.ecritic.ecritic_users_service.core.usecase.EmailResetUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserAddressUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserAddressesUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByIdUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUsersUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordChangeUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordResetRequestUseCase;
import com.ecritic.ecritic_users_service.core.usecase.PasswordResetUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserAddressUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserStatusUseCase;
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserUseCase;
import com.ecritic.ecritic_users_service.core.usecase.ValidateUserRoleUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserFilterMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;
import com.ecritic.ecritic_users_service.entrypoint.dto.ChangePasswordDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.PasswordResetDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserBanDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.fixture.AddressRequestDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.AddressResponseDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.AuthorizationTokenDataFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.ChangePasswordDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.PasswordResetDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserBanDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserRequestDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserResponseDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AddressDtoMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.UserDtoMapper;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @MockBean
    private FindUsersUseCase findUsersUseCase;

    @MockBean
    private FindUserByIdUseCase findUserByIdUseCase;

    @MockBean
    private CreateUserAddressUseCase createUserAddressUseCase;

    @MockBean
    private UpdateUserAddressUseCase updateUserAddressUseCase;

    @MockBean
    private FindUserAddressesUseCase findUserAddressesUseCase;

    @MockBean
    private FindUserAddressUseCase findUserAddressUseCase;

    @MockBean
    private EmailResetRequestUseCase emailResetRequestUseCase;

    @MockBean
    private EmailResetUseCase emailResetUseCase;

    @MockBean
    private PasswordResetRequestUseCase passwordResetRequestUseCase;

    @MockBean
    private PasswordResetUseCase passwordResetUseCase;

    @MockBean
    private PasswordChangeUseCase passwordChangeUseCase;

    @MockBean
    private UpdateUserStatusUseCase updateUserStatusUseCase;

    @MockBean
    private ValidateUserRoleUseCase validateUserRoleUseCase;

    @MockBean
    private AuthorizationTokenDataMapper authorizationTokenDataMapper;

    @MockBean
    private UserDtoMapper userDtoMapper;

    @MockBean
    private UserFilterMapper userFilterMapper;

    @MockBean
    private AddressDtoMapper addressDtoMapper;

    @Mock
    private Pageable pageable;

    private static final String AUTHORIZATION = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    void givenRequestToRegisterUser_thenCreate_andReturnUser() throws Exception {
        UserRequestDto userRequestDto = UserRequestDtoFixture.load();
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();
        User user = UserFixture.load();
        userRequestDto.setPassword("password");

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        when(userDtoMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(user);
        when(createUserUseCase.execute(any(User.class), anyString())).thenReturn(user);
        when(userDtoMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.description").value(userResponseDto.getDescription()))
                .andExpect(jsonPath("$.active").value(userResponseDto.isActive()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole()))
                .andExpect(jsonPath("$.country.id").value(userResponseDto.getCountry().getId()))
                .andExpect(jsonPath("$.country.name").value(userResponseDto.getCountry().getName()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        verify(createUserUseCase).execute(any(User.class), anyString());
    }

    @ParameterizedTest
    @MethodSource("provideUserCreateData")
    void givenRequestToRegisterUserWithInvalidParameters_thenReturnBadRequest(UserRequestDto userRequestDto, String message) throws Exception {
        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_06.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_06.getMessage()))
                .andExpect(jsonPath("$.detail").value(message))
                .andReturn();

        verifyNoInteractions(userDtoMapper);
        verifyNoInteractions(createUserUseCase);
    }

    @Test
    void givenRequestToUpdateUserEndpointWithValidParameters_thenReturnUpdatedUser() throws Exception {
        UserRequestDto userRequestDto = UserRequestDtoFixture.load();
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();
        User user = UserFixture.load();
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(userDtoMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(user);
        when(updateUserUseCase.execute(any(UUID.class), any(User.class))).thenReturn(user);
        when(userDtoMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{id}", authorizationTokenData.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.description").value(user.getDescription()))
                .andExpect(jsonPath("$.active").value(user.isActive()))
                .andExpect(jsonPath("$.role").value(user.getRole().toString()))
                .andExpect(jsonPath("$.country.id").value(user.getCountry().getId()))
                .andExpect(jsonPath("$.country.name").value(user.getCountry().getName()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        verify(updateUserUseCase).execute(any(UUID.class), any(User.class));
    }

    @Test
    void givenRequestToUpdateUserEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        UserRequestDto userRequestDto = UserRequestDtoFixture.load();
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();
        User user = UserFixture.load();
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(userDtoMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(user);
        when(updateUserUseCase.execute(any(UUID.class), any(User.class))).thenReturn(user);
        when(userDtoMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{id}", authorizationTokenData.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(updateUserUseCase);
    }

    @ParameterizedTest
    @MethodSource("provideUserUpdateData")
    void givenRequestToUpdateUserWithInvalidParameters_thenReturnBadRequest(UserRequestDto userRequestDto, String message) throws Exception {
        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_06.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_06.getMessage()))
                .andExpect(jsonPath("$.detail").value(message))
                .andReturn();

        verifyNoInteractions(userDtoMapper);
        verifyNoInteractions(updateUserUseCase);
    }

    @Test
    void givenRequestToUsersEndpoint_thenReturnAllUsers() throws Exception {
        List<User> users = List.of(UserFixture.load(), UserFixture.load(), UserFixture.load());
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();

        Page<User> userPage = new PageImpl<>(users, pageable, 3);

        when(findUsersUseCase.execute(any())).thenReturn(userPage);
        when(userDtoMapper.userToUserResponseDto(any())).thenReturn(userResponseDto, userResponseDto, userResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.data[0].name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.data[0].email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.data[0].description").value(userResponseDto.getDescription()))
                .andExpect(jsonPath("$.data[0].active").value(userResponseDto.isActive()))
                .andExpect(jsonPath("$.data[0].role").value(userResponseDto.getRole()))
                .andExpect(jsonPath("$.data[0].country.id").value(userResponseDto.getCountry().getId()))
                .andExpect(jsonPath("$.data[0].country.name").value(userResponseDto.getCountry().getName()))
                .andExpect(jsonPath("$.data[0].createdAt").isNotEmpty());

        verify(findUsersUseCase).execute(any());
    }

    @Test
    void givenRequestToGetUserEndpoint_thenReturnUser() throws Exception {
        User user = UserFixture.load();
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();

        when(authorizationTokenDataMapper.map(any())).thenReturn(AuthorizationTokenDataFixture.load());
        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(userDtoMapper.userToUserResponseDto(any())).thenReturn(userResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.description").value(userResponseDto.getDescription()))
                .andExpect(jsonPath("$.active").value(userResponseDto.isActive()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole()))
                .andExpect(jsonPath("$.country.id").value(userResponseDto.getCountry().getId()))
                .andExpect(jsonPath("$.country.name").value(userResponseDto.getCountry().getName()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        verify(findUserByIdUseCase).execute(user.getId());
    }

    @Test
    void givenRequestToGetUserEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(findUserByIdUseCase);
    }

    @Test
    void givenRequestToResetEmailRequestEndpointWithValidParameters_thenReturnResponseNoContent() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .email("jI8eU@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(AuthorizationTokenDataFixture.load());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/request-email-change")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    void givenRequestToResetEmailRequestEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .email("jI8eU@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/request-email-change")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(emailResetRequestUseCase);
    }

    @Test
    void givenRequestToResetEmailEndpoint_thenResetEmail_andReturnResponseNoContent() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/change-email", UUID.randomUUID())
                .param("token", "token")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(emailResetUseCase).execute(any(UUID.class), any(String.class));
    }

    @Test
    void givenRequestToForgotPasswordEndpoint_thenReturnResponseNoContent() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .email("jI8eU@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(userRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(passwordResetRequestUseCase).execute(any(String.class));
    }

    @Test
    void givenRequestToResetPasswordEndpoint_thenResetPassword_andReturnNoContent() throws Exception {
        PasswordResetDto passwordResetDto = PasswordResetDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(passwordResetDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/reset-password", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(passwordResetUseCase).execute(any(), anyString(), anyString(), anyString());
    }

    @Test
    void givenRequestToChangePasswordEndpoint_thenChangePassword_andReturnNoContent() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(changePasswordDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(AuthorizationTokenDataFixture.load());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/change-password", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(passwordChangeUseCase).execute(any(), anyString(), anyString(), anyString());
    }

    @Test
    void givenRequestToChangePasswordEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(changePasswordDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/change-password", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(passwordChangeUseCase);
    }

    @Test
    void givenRequestToChangeUserStatusEndpoint_thenChangeUserStatus_andReturnNoContent() throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        UserBanDto userBanDto = UserBanDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(userBanDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/status", authorizationTokenData.getUserId())
                .header("Authorization", AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(updateUserStatusUseCase).execute(authorizationTokenData.getUserId(), userBanDto.getMotive(), BanActionEnum.valueOf(userBanDto.getAction()));
    }

    @Test
    void givenRequestToChangeUserStatusEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        UserBanDto userBanDto = UserBanDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(userBanDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/status", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(updateUserStatusUseCase);
    }

    @ParameterizedTest
    @MethodSource("provideUserStatusUpdateData")
    void givenRequestToUpdateUserStatusWithInvalidParameters_ThenReturnBadRequest(UserBanDto userBanDto, String message) throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);

        String requestBody = objectMapper.writeValueAsString(userBanDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/status", authorizationTokenData.getUserId())
                .header("Authorization", AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_06.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_06.getMessage()))
                .andExpect(jsonPath("$.detail").value(message))
                .andReturn();

        verifyNoInteractions(updateUserStatusUseCase);
    }

    @Test
    void givenRequestToCreateAddressEndpoint_thenCreate_andReturnAddress() throws Exception {
        Address address = AddressFixture.load();
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        AddressRequestDto addressRequestDto = AddressRequestDtoFixture.load();
        AddressResponseDto addressResponseDto = AddressResponseDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(addressRequestDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(addressDtoMapper.addressRequestDtoToAddress(any())).thenReturn(address);
        when(createUserAddressUseCase.execute(any(), any())).thenReturn(address);
        when(addressDtoMapper.addressToAddressResponseDto(any())).thenReturn(addressResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/address", authorizationTokenData.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(addressResponseDto)));

        verify(addressDtoMapper).addressRequestDtoToAddress(any());
        verify(createUserAddressUseCase).execute(any(), any());
        verify(addressDtoMapper).addressToAddressResponseDto(any());
    }

    @Test
    void givenRequestToCreateAddressEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        AddressRequestDto addressRequestDto = AddressRequestDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(addressRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/address", authorizationTokenData.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(addressDtoMapper);
        verifyNoInteractions(createUserAddressUseCase);
        verifyNoInteractions(addressDtoMapper);
    }

    @ParameterizedTest
    @MethodSource("provideUserAddressCreateData")
    void givenRequestToCreateUserAddressEndpointWithInvalidParameters_thenReturnBadRequest(AddressRequestDto addressRequestDto, String message) throws Exception {
        String requestBody = objectMapper.writeValueAsString(addressRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/address", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_06.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_06.getMessage()))
                .andExpect(jsonPath("$.detail").value(message))
                .andReturn();

        verifyNoInteractions(addressDtoMapper);
        verifyNoInteractions(createUserAddressUseCase);
    }

    @Test
    void givenRequestToUpdateAddressEndpoint_thenUpdate_andReturnAddress() throws Exception {
        Address address = AddressFixture.load();
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        AddressRequestDto addressRequestDto = AddressRequestDtoFixture.load();
        AddressResponseDto addressResponseDto = AddressResponseDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(addressRequestDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(addressDtoMapper.addressRequestDtoToAddress(any())).thenReturn(address);
        when(updateUserAddressUseCase.execute(any(), any(), any())).thenReturn(address);
        when(addressDtoMapper.addressToAddressResponseDto(any())).thenReturn(addressResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{userId}/address/{addressId}", authorizationTokenData.getUserId(), address.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(addressResponseDto)));

        verify(addressDtoMapper).addressRequestDtoToAddress(any());
        verify(updateUserAddressUseCase).execute(any(), any(), any());
        verify(addressDtoMapper).addressToAddressResponseDto(any());
    }

    @Test
    void givenRequestToUpdateAddressEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        AddressRequestDto addressRequestDto = AddressRequestDtoFixture.load();

        String requestBody = objectMapper.writeValueAsString(addressRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{userId}/address/{addressId}", authorizationTokenData.getUserId(), UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(addressDtoMapper);
        verifyNoInteractions(updateUserAddressUseCase);
        verifyNoInteractions(addressDtoMapper);
    }

    @Test
    void givenRequestToFindUserAddressesEndpoint_thenFind_andReturnAddresses() throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        AddressResponseDto addressResponseDto = AddressResponseDtoFixture.load();
        List<Address> addresses = List.of(AddressFixture.load(), AddressFixture.load());
        List<AddressResponseDto> addressResponseDtos = List.of(addressResponseDto, addressResponseDto);

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(findUserAddressesUseCase.execute(any())).thenReturn(addresses);
        when(addressDtoMapper.addressToAddressResponseDto(any())).thenReturn(addressResponseDto, addressResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/address", authorizationTokenData.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(addressResponseDtos)));

        verify(findUserAddressesUseCase).execute(any());
    }

    @Test
    void givenRequestToFindUserAddressesEndpoint_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/address", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(findUserAddressesUseCase);
        verifyNoInteractions(addressDtoMapper);
    }

    @Test
    void givenRequestToFindUserAddressById_thenFind_andReturnAddress() throws Exception {
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();
        Address address = AddressFixture.load();
        AddressResponseDto addressResponseDto = AddressResponseDtoFixture.load();

        when(authorizationTokenDataMapper.map(any())).thenReturn(authorizationTokenData);
        when(findUserAddressUseCase.execute(any(), any())).thenReturn(address);
        when(addressDtoMapper.addressToAddressResponseDto(any())).thenReturn(addressResponseDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/address/{addressId}", authorizationTokenData.getUserId(), address.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", AUTHORIZATION);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(addressResponseDto)));

        verify(findUserAddressUseCase).execute(any(), any());
        verify(addressDtoMapper).addressToAddressResponseDto(any());
    }

    @Test
    void givenRequestToFindUserAddressById_whenAuthorizationTokenIsNotInformed_thenReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/address/{addressId}", UUID.randomUUID(), UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_02.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_02.getMessage()))
                .andExpect(jsonPath("$.detail").value("Required request header 'Authorization' for method parameter type String is not present"))
                .andReturn();

        verifyNoInteractions(findUserAddressUseCase);
        verifyNoInteractions(addressDtoMapper);
    }

    static Stream<Arguments> provideUserCreateData() {
        UserRequestDto user1 = UserRequestDtoFixture.load();
        user1.setName(null);

        UserRequestDto user2 = UserRequestDtoFixture.load();
        user2.setEmail(null);

        UserRequestDto user3 = UserRequestDtoFixture.load();
        user3.setPassword(null);

        UserRequestDto user4 = UserRequestDtoFixture.load();
        user4.setPasswordConfirmation(null);

        UserRequestDto user5 = UserRequestDtoFixture.load();
        user5.setPhone("123456789");

        UserRequestDto user6 = UserRequestDtoFixture.load();
        user6.setCountryId(null);

        return Stream.of(
                Arguments.of(user1, "name: Name is required"),
                Arguments.of(user2, "email: Email is required"),
                Arguments.of(user3, "password: Password is required"),
                Arguments.of(user4, "passwordConfirmation: Password confirmation is required"),
                Arguments.of(user5, "phone: Invalid phone number format"),
                Arguments.of(user6, "countryId: CountryId is required")
        );
    }

    static Stream<Arguments> provideUserUpdateData() {
        UserRequestDto user1 = UserRequestDtoFixture.load();
        user1.setName(null);

        UserRequestDto user2 = UserRequestDtoFixture.load();
        user2.setPhone("123456789");

        UserRequestDto user3 = UserRequestDtoFixture.load();
        user3.setCountryId(null);

        return Stream.of(
                Arguments.of(user1, "Name is required"),
                Arguments.of(user2, "Invalid phone number format"),
                Arguments.of(user3, "CountryId is required")
        );
    }

    static Stream<Arguments> provideUserAddressCreateData() {
        AddressRequestDto address1 = AddressRequestDtoFixture.load();
        address1.setCountryId(null);

        AddressRequestDto address2 = AddressRequestDtoFixture.load();
        address2.setUf(null);

        AddressRequestDto address3 = AddressRequestDtoFixture.load();
        address3.setCity(null);

        AddressRequestDto address4 = AddressRequestDtoFixture.load();
        address4.setNeighborhood(null);

        AddressRequestDto address5 = AddressRequestDtoFixture.load();
        address5.setStreet(null);

        AddressRequestDto address6 = AddressRequestDtoFixture.load();
        address6.setPostalCode(null);

        return Stream.of(
                Arguments.of(address1, "countryId: Country ID is required"),
                Arguments.of(address2, "uf: UF is required"),
                Arguments.of(address3, "city: City is required"),
                Arguments.of(address4, "neighborhood: Neighborhood is required"),
                Arguments.of(address5, "street: Street is required"),
                Arguments.of(address6, "postalCode: Postal code is required")
        );
    }

    static Stream<Arguments> provideUserStatusUpdateData() {
        UserBanDto userBanDto1 = UserBanDtoFixture.load();
        userBanDto1.setAction(null);

        UserBanDto userBanDto2 = UserBanDtoFixture.load();
        userBanDto2.setAction("banning");

        UserBanDto userBanDto3 = UserBanDtoFixture.load();
        userBanDto3.setMotive(null);

        return Stream.of(
                Arguments.of(userBanDto1, "action: Status update action is required"),
                Arguments.of(userBanDto2, "action: Action must be either BAN or UNBAN"),
                Arguments.of(userBanDto3, "motive: Motive is required")
        );
    }
}