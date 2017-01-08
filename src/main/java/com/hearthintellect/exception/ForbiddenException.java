package com.hearthintellect.exception;

public class ForbiddenException extends ErrorResponseException {
    public ForbiddenException(String message) {
        super(403, message);
    }
}
