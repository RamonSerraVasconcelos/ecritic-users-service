package com.ecritic.ecritic_users_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String description;
    private boolean active = true;
    private Role role = Role.DEFAULT;
    private Country country;
    private transient Long countryId;
    private String passwordResetHash;
    private LocalDateTime passwordResetDate;
    private String emailResetHash;
    private LocalDateTime emailResetDate;
    private String newEmailReset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
