package de.thk.parcer.parcer.resources;

/**
 * Wrapper that can be serialized to a json+hal-representation
 * for validation error messages.
 */
public class ValidationErrorResource {
    private final String validationError;

    public ValidationErrorResource(String validationError) {
        this.validationError = validationError;
    }

    public String getValidationError() {
        return validationError;
    }
}
