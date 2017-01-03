package com.hearthintellect.controller.exception;

public class ErrorResponseException extends RuntimeException {
    private final int statusCode;

    public ErrorResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() { return statusCode; }
}
