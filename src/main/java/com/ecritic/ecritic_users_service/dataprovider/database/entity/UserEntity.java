package com.ecritic.ecritic_users_service.dataprovider.database.entity;

import com.ecritic.ecritic_users_service.core.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@DynamicUpdate
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String description;

    private String phone;

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    private transient Long countryId;

    private String passwordResetHash;

    private LocalDateTime passwordResetDate;

    private String emailResetHash;

    private LocalDateTime emailResetDate;

    private String newEmailReset;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
