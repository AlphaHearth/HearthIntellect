package com.hearthintellect.exception;

public class UnauthorizedException extends ErrorResponseException {
    public UnauthorizedException(String message) {
        super(401, message);
    }
}
