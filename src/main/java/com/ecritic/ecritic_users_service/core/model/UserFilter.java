package com.ecritic.ecritic_users_service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserFilter {

    private List<UUID> userIds;
    private String name;
    private String email;
    private boolean active;
    private Pageable pageable;

    public boolean isCacheable() {
        return isNull(userIds) && isNull(name) && isNull(email) && active;
    }
}
