package com.ecritic.ecritic_users_service.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponseDto {

    private UUID id;
    private CountryResponseDto country;
    private String uf;
    private String city;
    private String neighborhood;
    private String street;
    private String postalCode;
    private String complement;
    private boolean isDefault;
}
