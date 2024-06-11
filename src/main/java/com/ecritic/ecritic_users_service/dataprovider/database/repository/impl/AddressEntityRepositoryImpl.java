package com.ecritic.ecritic_users_service.dataprovider.database.repository.impl;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.mapper.AddressEntityRowMapper;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.AddressEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AddressEntityRepositoryImpl implements AddressEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BASE_SELECT = "SELECT a.id, c.id as country_id, c.name as country_name, a.uf, a.city, a.neighborhood, a.street, a.postal_code, a.complement, ua.is_default FROM addresses a";

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

    @Override
    public List<AddressEntity> findUserAddressesByUserId(UUID userId) {
        String query = BASE_SELECT +
                " LEFT JOIN user_adresses ua ON a.id = ua.address_id" +
                " LEFT JOIN countries c ON a.country_id = c.id" +
                " WHERE ua.user_id = :userId " +
                " ORDER BY a.created_at DESC";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbcTemplate.query(query, (SqlParameterSource) params, (RowMapper<AddressEntity>) new AddressEntityRowMapper());
    }

    @Override
    public AddressEntity findUserAddressByIds(UUID userId, UUID addressId) {
        String query = BASE_SELECT +
                " LEFT JOIN user_adresses ua ON a.id = ua.address_id" +
                " LEFT JOIN countries c ON a.country_id = c.id" +
                " WHERE ua.user_id = :userId AND a.id = :addressId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("addressId", addressId);


        return jdbcTemplate.query(query, (SqlParameterSource) params, (ResultSetExtractor<AddressEntity>) new AddressEntityRowMapper());
    }
}
