package com.ecritic.ecritic_users_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String description;
    private String phone;
    private Country country;
    private List<Address> addresses;
    private Role role = Role.DEFAULT;
    private boolean active = true;
    private String passwordResetHash;
    private LocalDateTime passwordResetDate;
    private String emailResetHash;
    private LocalDateTime emailResetDate;
    private String newEmailReset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
