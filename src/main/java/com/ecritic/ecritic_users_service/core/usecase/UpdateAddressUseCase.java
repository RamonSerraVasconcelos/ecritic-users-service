package com.ecritic.ecritic_users_service.core.usecase;

import com.ecritic.ecritic_users_service.core.model.Address;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindAddressByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.FindCountryByIdBoundary;
import com.ecritic.ecritic_users_service.core.usecase.boundary.SaveAddressBoundary;
import com.ecritic.ecritic_users_service.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UpdateAddressUseCase extends AbstractAddressUseCase {

    private final FindAddressByIdBoundary findAddressByIdBoundary;

    UpdateAddressUseCase(FindCountryByIdBoundary findCountryByIdBoundary, SaveAddressBoundary saveAddressBoundary, FindAddressByIdBoundary findAddressByIdBoundary) {
        super(findCountryByIdBoundary, saveAddressBoundary);
        this.findAddressByIdBoundary = findAddressByIdBoundary;
    }

    public Address execute(UUID addressId, Address address) {
        log.info("Updating address with id: [{}}", addressId);

        try {
            validateCountry(address.getCountry().getId());

            Optional<Address> optionalAddress = findAddressByIdBoundary.execute(addressId);

            if (optionalAddress.isEmpty()) {
                throw new EntityNotFoundException("Address not found");
            }

            Address addressToUpdate = optionalAddress.get();

            addressToUpdate.setCountry(address.getCountry());
            addressToUpdate.setUf(address.getUf());
            addressToUpdate.setCity(address.getCity());
            addressToUpdate.setNeighborhood(address.getNeighborhood());
            addressToUpdate.setStreet(address.getStreet());
            addressToUpdate.setPostalCode(address.getPostalCode());
            addressToUpdate.setComplement(address.getComplement());

            return upsertAddress(addressToUpdate);
        } catch (Exception ex) {
            log.error("Error while updating address with id: [{}}", addressId, ex);
            throw ex;
        }
    }
}
