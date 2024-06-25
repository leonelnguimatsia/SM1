package de.thk.parcer.parcer.services;

import java.util.Set;

import de.thk.parcer.parcer.domain.Address;

/**
 * Interface with services concerning the detection of
 * flaws in postal addresses.
 */
public interface AddressValidationService {
    /**
     * Analyzes a given postal {@code address} for flaws.
     * @param address the address to analyze
     * @return a set of validation error messages
     */
    Set<String> getValidationErrors(Address address);
}