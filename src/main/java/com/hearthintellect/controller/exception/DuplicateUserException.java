package com.hearthintellect.controller.exception;

public class DuplicateUserException extends DuplicateEntityException {
    public DuplicateUserException(String username) {
        super("User", username);
    }
}
