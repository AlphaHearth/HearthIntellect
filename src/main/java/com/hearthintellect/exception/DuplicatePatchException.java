package com.hearthintellect.exception;

public class DuplicatePatchException extends DuplicateEntityException {
    public DuplicatePatchException(int patchBuildNum) {
        super("Patch", String.valueOf(patchBuildNum));
    }
}
