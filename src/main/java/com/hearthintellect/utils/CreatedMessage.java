package com.hearthintellect.utils;

public class CreatedMessage extends Message {
    private final String entityUrl;

    public CreatedMessage(String entityUrl, String message) {
        super(201, message);
        this.entityUrl = entityUrl;
    }

    public String getEntityUrl() { return entityUrl; }
}
