package com.hearthintellect.exception;

public class MechanicNotFoundException extends NotFoundException {
    public MechanicNotFoundException(String mechanicId) {
        super("Mechanic", mechanicId);
    }
}
