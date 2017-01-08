package com.hearthintellect.exception;

/**
 * Triggered when a client is trying to log in with invalid user credential.
 */
public class InvalidUserCredentialException extends UnauthorizedException {
    private static final String INVALID_CREDENTIAL_PROMPT = "The provided username or password is invalid.";

    public InvalidUserCredentialException() {
        super(INVALID_CREDENTIAL_PROMPT);
    }
}
