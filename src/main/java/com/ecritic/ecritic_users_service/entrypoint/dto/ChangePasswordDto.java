package com.ecritic.ecritic_users_service.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangePasswordDto {

    @NotBlank(message = "Password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;

    @NotBlank(message = "New password confirmation is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String passwordConfirmation;
}
