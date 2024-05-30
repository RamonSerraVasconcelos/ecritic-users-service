package com.ecritic.ecritic_users_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address {

    private Long id;
    private Country country;
    private String uf;
    private String city;
    private String neighborhood;
    private String street;
    private String postalCode;
    private String complement;
    private boolean isDefault;
}
