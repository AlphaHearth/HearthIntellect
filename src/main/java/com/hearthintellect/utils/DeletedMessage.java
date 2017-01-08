package com.hearthintellect.utils;

public class DeletedMessage extends Message {
    public DeletedMessage(String message) {
        super(204, message);
    }
}
