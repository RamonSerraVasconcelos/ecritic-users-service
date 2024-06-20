package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.model.UserFilter;
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
import com.ecritic.ecritic_users_service.core.usecase.UpdateUserUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.UserFilterMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationTokenData;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.UserResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.fixture.AuthorizationTokenDataFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserRequestDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.fixture.UserResponseDtoFixture;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AddressDtoMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.AuthorizationTokenDataMapper;
import com.ecritic.ecritic_users_service.entrypoint.mapper.UserDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

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

        ObjectMapper objectMapper = new ObjectMapper();
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
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole().toString()))
                .andExpect(jsonPath("$.country.id").value(userResponseDto.getCountry().getId()))
                .andExpect(jsonPath("$.country.name").value(userResponseDto.getCountry().getName()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        verify(createUserUseCase).execute(any(User.class), anyString());
    }

    @Test
    void givenRequestToUpdateUserEndpointWithValidParameters_thenReturnUpdatedUser() throws Exception {
        UserRequestDto userRequestDto = UserRequestDtoFixture.load();
        UserResponseDto userResponseDto = UserResponseDtoFixture.load();
        User user = UserFixture.load();
        AuthorizationTokenData authorizationTokenData = AuthorizationTokenDataFixture.load();

        ObjectMapper objectMapper = new ObjectMapper();
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
    void givenRequestToResetEmailRequestEndpointWithValidParameters_thenReturnResponseNoContent() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .email("jI8eU@example.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
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
    void givenRequestToResetEmailEndpoint_thenResetEmail_andReturnResponseNoContent() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/users/{userId}/change-email", UUID.randomUUID())
                .param("token", "token")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(emailResetUseCase).execute(any(UUID.class), any(String.class));
    }
}