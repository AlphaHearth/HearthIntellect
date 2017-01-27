package com.hearthintellect.exception;

public class DuplicateMechanicException extends DuplicateEntityException {
    public DuplicateMechanicException(String mechanicId) {
        super("Mechanic", mechanicId);
    }
}
