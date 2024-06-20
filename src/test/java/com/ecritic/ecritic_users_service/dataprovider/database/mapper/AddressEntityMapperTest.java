package com.ecritic.ecritic_users_service.dataprovider.database.mapper;


import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.dataprovider.database.entity.AddressEntity;
import com.ecritic.ecritic_users_service.dataprovider.database.fixture.AddressEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressEntityMapperTest {

    private AddressEntityMapper addressEntityMapper;

    @BeforeEach
    public void setUp() {
        addressEntityMapper = Mappers.getMapper(AddressEntityMapper.class);
    }

    @Test
    void givenAddress_thenReturnAddressEntity() {
        Address address = AddressFixture.load();

        AddressEntity addressEntity = addressEntityMapper.addressToAddressEntity(address);

        assertThat(addressEntity).usingRecursiveComparison().isEqualTo(address);
    }

    @Test
    void givenAddressEntity_thenReturnAddress() {
        AddressEntity addressEntity = AddressEntityFixture.load();

        Address address = addressEntityMapper.addressEntityToAddress(addressEntity);

        assertThat(address).usingRecursiveComparison().isEqualTo(addressEntity);
    }
}