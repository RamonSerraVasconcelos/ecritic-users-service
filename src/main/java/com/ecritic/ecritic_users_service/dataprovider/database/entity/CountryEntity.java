package com.ecritic.ecritic_users_service.dataprovider.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "countries")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {

    @Id
    private UUID id;

    private String name;
}
