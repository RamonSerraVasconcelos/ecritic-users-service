package com.ecritic.ecritic_users_service.dataprovider.database.repository.impl;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.BanActionEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserBanListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserBanListRepositoryImpl implements UserBanListRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void updateBanList(UUID userId, String motive, BanActionEntity action) {
        String query = "INSERT INTO banlist(user_id, motive, action) VALUES (CAST(:userId AS UUID), :motive, :action)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId.toString());
        params.addValue("motive", motive);
        params.addValue("action", action.name());

        jdbcTemplate.update(query, params);
    }
}
