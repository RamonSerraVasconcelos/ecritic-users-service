package com.ecritic.ecritic_users_service.dataprovider.database.repository.impl;

import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AddressEntityRepositoryImpl implements AddressEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveUserAddress(UUID userId, UUID addressId) {
        String query = "INSERT INTO user_adresses (user_id, address_id, is_default) VALUES (:userId, :addressId, false)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("addressId", addressId);

        jdbcTemplate.update(query, params);
    }


    @Override
    public void unsetUserDefaultAddress(UUID userId) {
        String query = "UPDATE user_adresses SET is_default = false WHERE user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);

        jdbcTemplate.update(query, params);
    }


    @Override
    public void setUserDefaultAddress(UUID userId, UUID addressId) {
        String query = "UPDATE user_adresses SET is_default = true WHERE user_id = :userId AND address_id = :addressId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("addressId", addressId);

        jdbcTemplate.update(query, params);
    }
}
