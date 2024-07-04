package com.ecritic.ecritic_users_service.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserBanDto {

    @NotBlank(message = "Status update action is required")
    @Pattern(regexp = "^(BAN|UNBAN)$", message = "Action must be either BAN or UNBAN")
    private String action;

    @NotBlank(message = "Motive is required")
    @Length(min = 4, message = "Motive must be at least 8 characters long")
    private String motive;
}
