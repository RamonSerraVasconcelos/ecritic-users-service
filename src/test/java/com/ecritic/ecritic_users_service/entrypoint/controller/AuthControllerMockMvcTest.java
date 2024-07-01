package com.ecritic.ecritic_users_service.entrypoint.controller;

import com.ecritic.ecritic_users_service.core.fixture.UserFixture;
import com.ecritic.ecritic_users_service.core.model.User;
import com.ecritic.ecritic_users_service.core.usecase.FindUserByEmailUseCase;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AuthorizationInfoMapper;
import com.ecritic.ecritic_users_service.entrypoint.dto.AuthorizationInfo;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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
    private AuthorizationInfoMapper authorizationInfoMapper;

    @Test
    void givenRequestToGetUserAuthInfoEndpoint_thenReturnAuthorizationInfo() throws Exception {
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
    }
}