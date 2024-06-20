package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserFilterMapperTest {

    private UserFilterMapper userFilterMapper;

    @Mock
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        userFilterMapper = Mappers.getMapper(UserFilterMapper.class);
    }

    @Test
    void givenParameters_thenReturnUserFilter() {
        List<UUID> userIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        UserFilter userFilter = userFilterMapper.map(pageable, true, "name", "email", List.of(userIds.get(0).toString(), userIds.get(1).toString()));

        assertThat(userFilter).isNotNull();
        assertThat(userFilter.isActive()).isTrue();
        assertThat(userFilter.getName()).isEqualTo("name");
        assertThat(userFilter.getEmail()).isEqualTo("email");
        assertThat(userFilter.getUserIds()).isNotNull().contains(userIds.toArray(new UUID[0]));
    }
}