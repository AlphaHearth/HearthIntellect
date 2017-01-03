package com.hearthintellect.utils;

public class Message {
    private final int statusCode;
    private final String message;

    public Message(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
}
