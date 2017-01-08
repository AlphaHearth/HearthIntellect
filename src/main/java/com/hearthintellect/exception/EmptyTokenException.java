package com.hearthintellect.exception;

public class EmptyTokenException extends BadRequestException {
    public EmptyTokenException() {
        super("Token must be provided to use this API.");
    }
}
