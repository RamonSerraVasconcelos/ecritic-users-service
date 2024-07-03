package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByEmailUseCase;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByIdUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AuthorizationInfoMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
import com.ecritic.ecritic_users_service.exception.handler.ErrorResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @MockBean
    private FindUserByIdUseCase findUserByIdUseCase;

    @MockBean
    private AuthorizationInfoMapper authorizationInfoMapper;

    @Test
    void givenRequestToGetUserAuthInfoEndpoint_whenUsingEmailParameter_thenReturnAuthorizationInfo() throws Exception {
        User user = UserFixture.load();

        AuthorizationInfo authorizationInfo = AuthorizationInfo.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .active(user.isActive())
                .build();

        when(findUserByEmailUseCase.execute(anyString())).thenReturn(user);
        when(authorizationInfoMapper.userToAuthorizationInfo(user)).thenReturn(authorizationInfo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/auth/info")
                .queryParam("email", user.getEmail())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorizationInfo.getId()))
                .andExpect(jsonPath("$.email").value(authorizationInfo.getEmail()))
                .andExpect(jsonPath("$.password").value(authorizationInfo.getPassword()))
                .andExpect(jsonPath("$.active").value(authorizationInfo.isActive()))
                .andExpect(jsonPath("$.role").value(authorizationInfo.getRole()));

        verify(findUserByEmailUseCase).execute(anyString());
        verify(authorizationInfoMapper).userToAuthorizationInfo(user);
        verifyNoInteractions(findUserByIdUseCase);
    }

    @Test
    void givenRequestToGetUserAuthInfoEndpoint_whenUsingIdParameter_thenReturnAuthorizationInfo() throws Exception {
        User user = UserFixture.load();

        AuthorizationInfo authorizationInfo = AuthorizationInfo.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .active(user.isActive())
                .build();

        when(findUserByIdUseCase.execute(any(UUID.class))).thenReturn(user);
        when(authorizationInfoMapper.userToAuthorizationInfo(user)).thenReturn(authorizationInfo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/auth/info")
                .queryParam("userId", user.getId().toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorizationInfo.getId()))
                .andExpect(jsonPath("$.email").value(authorizationInfo.getEmail()))
                .andExpect(jsonPath("$.password").value(authorizationInfo.getPassword()))
                .andExpect(jsonPath("$.active").value(authorizationInfo.isActive()))
                .andExpect(jsonPath("$.role").value(authorizationInfo.getRole()));

        verify(findUserByIdUseCase).execute(any(UUID.class));
        verify(authorizationInfoMapper).userToAuthorizationInfo(user);
        verifyNoInteractions(findUserByEmailUseCase);
    }

    @Test
    void givenRequestWithoutParameters_thenReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/auth/info")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorResponseCode.ECRITICUSERS_06.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorResponseCode.ECRITICUSERS_06.getMessage()))
                .andExpect(jsonPath("$.detail").value(ErrorResponseCode.ECRITICUSERS_01.getDetail()))
                .andReturn();

        verifyNoInteractions(findUserByEmailUseCase);
        verifyNoInteractions(findUserByIdUseCase);
        verifyNoInteractions(authorizationInfoMapper);
    }
}