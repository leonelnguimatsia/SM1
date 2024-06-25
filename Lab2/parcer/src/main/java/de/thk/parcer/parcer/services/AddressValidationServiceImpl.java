package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Address;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddressValidationServiceImpl implements AddressValidationService {

    @Override
    public Set<String> getValidationErrors(Address address) {
        // For now, this is merely a dummy implementation.
        // Validating postal addresses is a research field on its own.
        Set<String> result = new HashSet<>();
        String concatAddress = address.getLine1() + "," + address.getLine2() + "," + address.getLine3();
        if (!concatAddress.matches("^.*\\d{5}.*$")) {
            result.add("no german zip code found");
        }
        return result;
    }
}