package com.hearthintellect.exception;

/**
 * Triggered when certain type of resource is not found in the system.
 */
public class NotFoundException extends ErrorResponseException {
    private static final String ENTITY_NOT_FOUND_PROMPT = "%s with ID `%s` does not exist.";

    public NotFoundException(String entityName, String entityId) {
        super(404, String.format(ENTITY_NOT_FOUND_PROMPT, entityName, entityId));
    }
}
