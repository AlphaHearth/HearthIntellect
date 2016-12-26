package com.hearthintellect.controller.exception;

/**
 * Triggered when certain type of entity is not found in the system.
 */
public class EntityNotFoundException extends RuntimeException {
    private final String entityName;
    private final String entityId;

    public EntityNotFoundException(String entityName, String entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public String getEntityName() { return entityName; }
    public String getEntityId() { return entityId; }
}
