package com.hearthintellect.controller.exception;

public class EmptyTokenException extends BadRequestException {
    public EmptyTokenException() {
        super("Token cannot be empty.");
    }
}
