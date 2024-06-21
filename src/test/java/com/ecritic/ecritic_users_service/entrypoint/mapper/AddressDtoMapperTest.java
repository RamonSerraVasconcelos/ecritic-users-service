package com.ecritic.ecritic_users_service.entrypoint.mapper;

import com.ecritic.ecritic_users_service.core.fixture.AddressFixture;
import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressRequestDto;
import com.ecritic.ecritic_users_service.entrypoint.dto.AddressResponseDto;
import com.ecritic.ecritic_users_service.entrypoint.fixture.AddressRequestDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressDtoMapperTest {

    private AddressDtoMapper addressDtoMapper;

    @BeforeEach
    public void setUp() {
        addressDtoMapper = Mappers.getMapper(AddressDtoMapper.class);
    }

    @Test
    void givenAddressRequestDto_thenReturnAddress() {
        AddressRequestDto addressRequestDto = AddressRequestDtoFixture.load();

        Address address = addressDtoMapper.addressRequestDtoToAddress(addressRequestDto);

        assertThat(address).isNotNull();
        assertThat(address.getCountry().getId()).isEqualTo(addressRequestDto.getCountryId());
        assertThat(address.getUf()).isEqualTo(addressRequestDto.getUf());
        assertThat(address.getCity()).isEqualTo(addressRequestDto.getCity());
        assertThat(address.getNeighborhood()).isEqualTo(addressRequestDto.getNeighborhood());
        assertThat(address.getStreet()).isEqualTo(addressRequestDto.getStreet());
        assertThat(address.getPostalCode()).isEqualTo(addressRequestDto.getPostalCode());
        assertThat(address.getComplement()).isEqualTo(addressRequestDto.getComplement());
        assertThat(address.isDefault()).isEqualTo(addressRequestDto.isDefault());
    }

    @Test
    void givenAddress_thenReturnAddressResponseDto() {
        Address address = AddressFixture.load();

        AddressResponseDto addressResponseDto = addressDtoMapper.addressToAddressResponseDto(address);

        assertThat(addressResponseDto).isNotNull();
        assertThat(addressResponseDto.getCountry().getId()).isEqualTo(address.getCountry().getId());
        assertThat(addressResponseDto.getCountry().getName()).isEqualTo(address.getCountry().getName());
        assertThat(addressResponseDto.getUf()).isEqualTo(address.getUf());
        assertThat(addressResponseDto.getCity()).isEqualTo(address.getCity());
        assertThat(addressResponseDto.getNeighborhood()).isEqualTo(address.getNeighborhood());
        assertThat(addressResponseDto.getStreet()).isEqualTo(address.getStreet());
        assertThat(addressResponseDto.getPostalCode()).isEqualTo(address.getPostalCode());
        assertThat(addressResponseDto.getComplement()).isEqualTo(address.getComplement());
        assertThat(addressResponseDto.isDefault()).isEqualTo(address.isDefault());
    }
}