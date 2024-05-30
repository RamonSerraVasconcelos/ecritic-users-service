package com.ecritic.ecritic_users_service.dataprovider.database.repository.impl;

import com.ecritic.ecritic_users_service.core.model.UserFilter;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.UserEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.repository.UserEntityCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserEntityRepositoryImpl implements UserEntityCustomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT = "SELECT u.id, u.name, u.email, u.phone, u.description, u.country_id, u.role, u.active FROM users u";
    private static final String SELECT_COUNT = "SELECT COUNT(u.id) FROM users u";

    @Override
    public List<UserEntity> findUserListByParams(UserFilter userFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(userFilter, queryBuilder, params);

        String query = getFinalQuery(userFilter, queryBuilder, params);

        return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public Long countUsersByParams(UserFilter userFilter) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_COUNT);
        MapSqlParameterSource params = new MapSqlParameterSource();

        addConditions(userFilter, queryBuilder, params);

        return jdbcTemplate.queryForObject(queryBuilder.toString(), params, Long.class);
    }

    private void addConditions(UserFilter userFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        queryBuilder.append(" WHERE u.active = :active");
        params.addValue("active", userFilter.isActive());

        if (userFilter.getUserIds() != null && !userFilter.getUserIds().isEmpty()) {
            queryBuilder.append(" AND u.id IN (:userIds)");
            params.addValue("userIds", userFilter.getUserIds());
        }

        if (userFilter.getName() != null && !userFilter.getName().isEmpty()) {
            String name = userFilter.getName();
            String trimmedName = name.substring(0, name.lastIndexOf(name.trim()) + name.trim().length()).replace(" ", " & ");

            queryBuilder.append(" AND to_tsvector(u.name) @@ to_tsquery(:name)");
            params.addValue("name", trimmedName);
        }

        if (userFilter.getEmail() != null && !userFilter.getEmail().isEmpty()) {
            queryBuilder.append(" AND u.email = :email");
            params.addValue("email", userFilter.getEmail());
        }
    }

    private String getFinalQuery(UserFilter userFilter, StringBuilder queryBuilder, MapSqlParameterSource params) {
        int offset = Math.abs(userFilter.getPageable().getPageNumber() * userFilter.getPageable().getPageSize());
        params.addValue("pageSize", userFilter.getPageable().getPageSize());
        params.addValue("offset", offset);

        queryBuilder.append(" ORDER BY u.name ASC LIMIT :pageSize OFFSET :offset");
        return queryBuilder.toString();
    }
}
