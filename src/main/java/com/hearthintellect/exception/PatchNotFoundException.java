package com.hearthintellect.exception;

public class PatchNotFoundException extends NotFoundException {
    public PatchNotFoundException(int patchBuildNum) {
        super("Patch", String.valueOf(patchBuildNum));
    }
}
