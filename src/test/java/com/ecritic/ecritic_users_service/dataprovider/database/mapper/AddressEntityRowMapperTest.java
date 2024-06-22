package com.ecritic.ecritic_users_service.dataprovider.database.mapper;

import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.AddressEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressEntityRowMapperTest {

    @InjectMocks
    private AddressEntityRowMapper addressEntityRowMapper;

    @Mock
    private ResultSet rs;

    @Test
    public void testMapRow() throws SQLException {
        AddressEntity addressEntityFixture = AddressEntityFixture.load();

        when(rs.getObject("id", UUID.class)).thenReturn(addressEntityFixture.getId());
        when(rs.getObject("country_id", Long.class)).thenReturn(addressEntityFixture.getCountry().getId());
        when(rs.getString("country_name")).thenReturn(addressEntityFixture.getCountry().getName());
        when(rs.getString("uf")).thenReturn(addressEntityFixture.getUf());
        when(rs.getString("city")).thenReturn(addressEntityFixture.getCity());
        when(rs.getString("neighborhood")).thenReturn(addressEntityFixture.getNeighborhood());
        when(rs.getString("street")).thenReturn(addressEntityFixture.getStreet());
        when(rs.getString("postal_code")).thenReturn(addressEntityFixture.getPostalCode());
        when(rs.getString("complement")).thenReturn(addressEntityFixture.getComplement());
        when(rs.getBoolean("is_default")).thenReturn(addressEntityFixture.isDefault());

        AddressEntity addressEntity = addressEntityRowMapper.mapRow(rs, 1);

        assertThat(addressEntity).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(addressEntityFixture);
    }

    @Test
    public void testExtractData() throws SQLException {
        AddressEntity addressEntityFixture = AddressEntityFixture.load();

        when(rs.next()).thenReturn(true);
        when(rs.getObject("id", UUID.class)).thenReturn(addressEntityFixture.getId());
        when(rs.getObject("country_id", Long.class)).thenReturn(addressEntityFixture.getCountry().getId());
        when(rs.getString("country_name")).thenReturn(addressEntityFixture.getCountry().getName());
        when(rs.getString("uf")).thenReturn(addressEntityFixture.getUf());
        when(rs.getString("city")).thenReturn(addressEntityFixture.getCity());
        when(rs.getString("neighborhood")).thenReturn(addressEntityFixture.getNeighborhood());
        when(rs.getString("street")).thenReturn(addressEntityFixture.getStreet());
        when(rs.getString("postal_code")).thenReturn(addressEntityFixture.getPostalCode());
        when(rs.getString("complement")).thenReturn(addressEntityFixture.getComplement());
        when(rs.getBoolean("is_default")).thenReturn(addressEntityFixture.isDefault());

        AddressEntity addressEntity = addressEntityRowMapper.extractData(rs);

        assertThat(addressEntity).usingRecursiveComparison().ignoringFields("createdAt", "updatedAt").isEqualTo(addressEntityFixture);
    }
}