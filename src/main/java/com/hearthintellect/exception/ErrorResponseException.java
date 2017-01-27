package com.hearthintellect.exception;

public class ErrorResponseException extends RuntimeException {
    private final int statusCode;

    ErrorResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() { return statusCode; }
}
