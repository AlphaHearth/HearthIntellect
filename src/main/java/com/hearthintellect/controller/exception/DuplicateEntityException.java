package com.hearthintellect.controller.exception;

/**
 * Triggered when user is trying to create certain type of entity while entity with the
 * same ID already exists in the system.
 */
public class DuplicateEntityException extends ForbiddenException {
    private static final String DUPLICATE_ENTITY_PROMPT = "%s with given ID `%s` already exists.";

    public DuplicateEntityException(String entityName, String entityId) {
        super(String.format(DUPLICATE_ENTITY_PROMPT, entityName, entityId));
    }
}
