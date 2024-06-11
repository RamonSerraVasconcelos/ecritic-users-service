package com.ecritic.ecritic_users_service.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressRequestDto {

    private Long countryId;

    @Size(min = 2, max = 2, message = "Invalid UF")
    private String uf;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Postal code is required")
    @Size(min = 9, max = 9, message = "Invalid postal code")
    private String postalCode;

    @Size(max = 100, message = "Invalid complement")
    private String complement;

    private boolean isDefault;
}
