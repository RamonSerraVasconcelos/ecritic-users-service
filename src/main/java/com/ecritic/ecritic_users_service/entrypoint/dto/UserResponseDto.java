package com.ecritic.ecritic_users_service.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String id;
    private String name;
    private String email;
    private String description;
    private String phone;
    private boolean active;
    private String role;
    private CountryResponseDto country;
    private LocalDateTime createdAt;
}
