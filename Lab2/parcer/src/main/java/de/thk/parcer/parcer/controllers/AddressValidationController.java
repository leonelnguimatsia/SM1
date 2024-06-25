package de.thk.parcer.parcer.controllers;

import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.resources.ValidationErrorResource;
import de.thk.parcer.parcer.services.AddressValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that handles incoming restful calls, which concern address validation.
 */
@RestController
@RequestMapping(produces = "application/hal+json")
public class AddressValidationController {

    private final AddressValidationService addressValidationService;

    @Autowired
    public AddressValidationController(final AddressValidationService addressValidationService) {
        this.addressValidationService = addressValidationService;
    }

    /**
     * Analyzes a given address for flaws, e.g., an invalid zip code.
     * <p>
     * Example call with {@code curl}:
     *
     * <pre class="code">
     *  curl -sSL -D - -H "Content-Type: application/json" -X POST -d '{"line1": "Rene Woerzberger", "line2": "Claudiusstrasse 16", "line3": "5150 Roesrath"}' http://localhost:8080/addressValidator
     * </pre>
     *
     * @param address the address to validate
     * @return a list of validation errors, which might be empty
     */
    @RequestMapping(value = "/addressValidator", method = RequestMethod.POST)
    public ResponseEntity<Resources<ValidationErrorResource>> validateAddress(@RequestBody Address address) {
        final Set<String> validationErrors = addressValidationService.getValidationErrors(address);
        Resources<ValidationErrorResource> validationErrorResources = new Resources<>(validationErrors.stream().map(ValidationErrorResource::new).collect(Collectors.toList()));
        return ResponseEntity.ok(validationErrorResources);
    }
}
