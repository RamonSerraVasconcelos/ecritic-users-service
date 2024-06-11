package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.CountryEntity;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AddressEntityRowMapper implements RowMapper<AddressEntity>, ResultSetExtractor<AddressEntity> {

    @Override
    public AddressEntity mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        return getAddressEntity(rs);
    }

    @Override
    public AddressEntity extractData(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return getAddressEntity(rs);
        }
        return null;
    }

    private AddressEntity getAddressEntity(ResultSet rs) throws SQLException {
        return AddressEntity.builder()
                .id(rs.getObject("id", UUID.class))
                .country(CountryEntity.builder()
                        .id(rs.getObject("country_id", Long.class))
                        .name(rs.getString("country_name"))
                        .build())
                .uf(rs.getString("uf"))
                .city(rs.getString("city"))
                .neighborhood(rs.getString("neighborhood"))
                .street(rs.getString("street"))
                .postalCode(rs.getString("postal_code"))
                .complement(rs.getString("complement"))
                .isDefault(rs.getBoolean("is_default"))
                .build();
    }
}
