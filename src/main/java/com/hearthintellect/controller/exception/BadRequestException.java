package com.hearthintellect.controller.exception;

/**
 * Triggered when an invalid request is received.
 */
public class BadRequestException extends ErrorResponseException {
    public BadRequestException(String message) {
        super(400, message);
    }
}
